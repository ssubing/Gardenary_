package com.gardenary.domain.flower.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.exp.entity.Exp;
import com.gardenary.domain.exp.repository.ExpRepository;
import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.flower.entity.Flower;
import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.entity.QuestionAnswer;
import com.gardenary.domain.flower.mapper.QuestionAnswerMapper;
import com.gardenary.domain.flower.repository.FlowerRepository;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.flower.repository.QuestionAnswerRepository;
import com.gardenary.domain.flower.response.AnswerCompleteResponseDto;
import com.gardenary.domain.flower.response.MyFlowerOnlyIdResponseDto;
import com.gardenary.domain.flower.response.QuestionAnswerResponseDto;
import com.gardenary.domain.flower.response.QuestionAnswerListResponseDto;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.FlowerApiException;
import com.gardenary.global.error.exception.GrowingPlantApiException;
import com.gardenary.global.error.model.FlowerErrorCode;
import com.gardenary.global.error.model.GrowingPlantErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlowerServiceImpl implements FlowerService{

    private final GrowingPlantRepository growingPlantRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final ExpRepository expRepository;
    private final FlowerRepository flowerRepository;
    @Override
    @Transactional
    public AnswerCompleteResponseDto createAnswer(User user, QuestionAnswerDto questionAnswerDto) {
        
        AnswerCompleteResponseDto result = new AnswerCompleteResponseDto();
        //시간 판별,추후에 프론트에서 작성시작 시간을 받으면 코드 처리 하는 것으로 수정 가능성있음.
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime;
        LocalDateTime endTime;
        if(time.getHour() >= 3){
            startTime = time.withHour(3).withMinute(0).withSecond(0);
            endTime = startTime.plusDays(1).minusSeconds(1);
        } else{
            startTime = time.withHour(3).withMinute(0).withSecond(0).minusDays(1);
            endTime = startTime.plusDays(1).minusSeconds(1);
        }
        if(questionAnswerDto.isOver()){
            time.withHour(2).withMinute(59).withSecond(59);
            startTime = time.withHour(3).withMinute(0).withSecond(0).minusDays(1);
            endTime = startTime.plusDays(1).minusSeconds(1);
        }
        //오늘 이미 작성했는지 확인하기(가장 최근 것 가져와서 했는지 가져와서 확인, 추후에 함수로 빼기)
        List<QuestionAnswer> list = questionAnswerRepository.findAllByMyFlower_UserOrderByCreatedAtDesc(user);
        QuestionAnswer lastQA = list.get(0);
        LocalDateTime lastTime = lastQA.getCreatedAt();
        if(lastTime.isAfter(startTime) && lastTime.isBefore(endTime)){
            return null;
        }
        //현재 식물 가져오기
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        if(growingPlant.getId() == 0) {
            throw new GrowingPlantApiException(GrowingPlantErrorCode.GROWING_PLANT_NOT_FOUND);
        }
        //연속 작성 확인
        if(lastTime.isAfter(startTime.minusDays(1)) && lastTime.isBefore(endTime.minusDays(1))){
            growingPlant.modifyAnswerDays(growingPlant.getAnswerDays()+1);
        } else{
            growingPlant.modifyAnswerDays(1);
        }
        if(growingPlant.getAnswerDays()%3 == 0) {
            result.updateIsItem(true);
        } else{
            result.updateIsItem(false);
        }
        //총 답변 갯수 수정
        growingPlant.modifyAnswerCnt(growingPlant.getAnswerCnt() + 1);
        //유저 id로 현재 꽃, 꽃 아이디 찾기
        MyFlower currentFlower = growingPlant.getMyFlower();
        if(currentFlower.getId() == 0){
            throw new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND);
        }
        //캐시에서 현재 질문아이디 가져오기(캐시 설정 후 수정 필요)
        int questionId = 0;

        //Dto에 유저 정보, 질문 정보, 꽃 정보 시간, 질문 번호
        QuestionAnswerDto saveQuestionAnswerDto = QuestionAnswerDto.builder()
                .flowerId(currentFlower.getFlower().getId())
                .createdAt(time)
                .myFlowerId(currentFlower.getId())
                .questionId(questionId)
                .userId(user.getId())
                .content(questionAnswerDto.getContent())
                .questionNum(growingPlant.getAnswerCnt())
                .build();
        //내용 저장
        questionAnswerRepository.save(QuestionAnswerMapper.mapper.toEntity(saveQuestionAnswerDto));
        //경험치 기록 추가
        Exp exp = Exp.builder()
                .expAmount(10)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .diaryId(currentFlower.getId())
                .type(true)
                .user(user)
                .build();
        expRepository.save(exp);
        //경험치 증가(Redis) totalExp 수정 필요

        int totalExp = 100;
        //return 값 만들기
        result.updateTotalExp(totalExp);
        return result;
    }

    @Override
    public QuestionAnswerListResponseDto getOneFlowerAnswerList(User user, int myFlowerId) {
        //해당 유저와 내 꽃 아이디에 대해 조회 (에러까지 확인)
        MyFlower myFlower = myFlowerRepository.findById(myFlowerId);
        if(myFlower == null){
            throw new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND);
        }
        //엔티티 리스트
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllByMyFlowerAndMyFlower_UserOrderByCreatedAtDesc(myFlower, user);

        //리턴한 Dto 리스트로 만들기
        List<QuestionAnswerResponseDto> result = makeAnswerDtoList(questionAnswerList);
        return QuestionAnswerListResponseDto.builder()
                .questionAnswerResponseDtoList(result)
                .build();
    }

    @Override
    public QuestionAnswerListResponseDto getAllFlowerAnswerList(User user) {
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllByMyFlower_UserOrderByCreatedAtDesc(user);
        List<QuestionAnswerResponseDto> result = makeAnswerDtoList(questionAnswerList);
        return QuestionAnswerListResponseDto.builder()
                .questionAnswerResponseDtoList(result)
                .build();
    }


    private  List<QuestionAnswerResponseDto> makeAnswerDtoList(List<QuestionAnswer> questionAnswerList){
        List<QuestionAnswerResponseDto> result = new ArrayList<>();

        for (QuestionAnswer questionAnswer : questionAnswerList){
            QuestionAnswerResponseDto questionAnswerResponseDto = QuestionAnswerResponseDto.builder()
                    .createdAt(questionAnswer.getCreatedAt())
                    .question(questionAnswer.getQuestion().getContent())
                    .content(questionAnswer.getContent())
                    .questionNum(questionAnswer.getQuestionNum())
                    .build();
            result.add(questionAnswerResponseDto);
        }
        return result;
    }

    @Override
    public MyFlowerOnlyIdResponseDto createNewFlower(User user) {
        //꽃의 총 경험치를 가져오기 (수정 예정)
        int totalExp = 0;
        if(totalExp == 0 || (totalExp % 100) != 0) {
            throw new FlowerApiException(FlowerErrorCode.NOT_ENOUGH_EXP);
        }
        List<Flower> flowerList = flowerRepository.findAll();
        int size = flowerList.size();
        Random random = new Random(System.nanoTime());
        int num = random.nextInt(size);
        Flower newFlower = flowerList.get(num);
        //새로운 꽃 MyFlower에 넣어주기
        MyFlower myFlower = MyFlower.builder()
                .flower(newFlower)
                .user(user)
                .build();
        myFlowerRepository.save(myFlower);
        //Current 새로운 꽃으로 바꿔주기
        GrowingPlant current = growingPlantRepository.findByUser(user);
        current.modifyMyFlower(myFlower);
        growingPlantRepository.save(current);
        return MyFlowerOnlyIdResponseDto.builder()
                .id(current.getMyFlower().getId())
                .build();
    }

}
