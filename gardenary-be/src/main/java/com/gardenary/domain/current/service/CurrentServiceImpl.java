package com.gardenary.domain.current.service;

import com.gardenary.domain.current.dto.response.GrowingPlantResponseDto;
import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.flower.entity.QuestionAnswer;
import com.gardenary.domain.flower.repository.QuestionAnswerRepository;
import com.gardenary.domain.flower.repository.QuestionRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.GrowingPlantApiException;
import com.gardenary.global.error.model.GrowingPlantErrorCode;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentServiceImpl implements CurrentService{

    private final GrowingPlantRepository growingPlantRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionRepository questionRepository;
    private final RedisService redisService;
    @Override
    public GrowingPlantResponseDto getCurrentInfo(User user) {
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        boolean flowerCheck = false;
        //현재 시각을 찾아서 기준 시간(시작, 끝) 만들기
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
        //오늘 작성했는지 확인
//        List<QuestionAnswer> list = questionAnswerRepository.findAllByMyFlower_UserOrderByCreatedAtDesc(user);
//        if(list.size() != 0){
//            QuestionAnswer lastQA = list.get(0);
//            LocalDateTime lastTime = lastQA.getCreatedAt();
//            if(lastTime.isAfter(startTime) && lastTime.isBefore(endTime)){
//                flowerCheck = true;
//            }
//        }
        //꽃 경험치, 나무 경험치 캐시에서 가져오기
        int flowerTotalExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId()+"flowerExp"));
        int treeTotalExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId()+"treeExp"));
        //질문아이디 캐시에서 가져오고 해당 아이디로 질문 조회
        int questionId = Integer.parseInt(redisService.getStringValue(user.getKakaoId()));
        String question = questionRepository.findById(questionId).getContent();
        if(question == null){
            throw new GrowingPlantApiException(GrowingPlantErrorCode.QUESTION_NOT_FOUND);
        }
        return GrowingPlantResponseDto.builder()
                .flowerExp(flowerTotalExp)
                .treeExp(treeTotalExp)
                .isWrite(flowerCheck)
                .question(question)
                .questionNum(growingPlant.getAnswerCnt()+1)
                .flowerDays(growingPlant.getAnswerDays())
                .treeDays(growingPlant.getDiaryDays())
                .build();
    }

    @Override
    public void test(User user) {
        redisService.setValue(user.getKakaoId()+"flowerExp", String.valueOf(0));
        redisService.setValue(user.getKakaoId()+"treeExp", String.valueOf(175));
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        growingPlant.modifyAnswerCnt(0);
        growingPlant.modifyAnswerDays(growingPlant.getAnswerDays() -1);
        growingPlant.modifyDiaryDays(growingPlant.getDiaryDays() -1);
    }


}
