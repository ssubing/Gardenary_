package com.gardenary.domain.user.service;

import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.AvatarRepository;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.flower.entity.Flower;
import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.flower.service.FlowerServiceImpl;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.entity.Tree;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.tree.service.TreeServiceImpl;
import com.gardenary.domain.user.entity.Role;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialServiceImpl implements SocialService {

    private final UserRepository userRepository;
    private final MyAvatarRepository myAvatarRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final MyTreeRepository myTreeRepository;
    private final GrowingPlantRepository growingPlantRepository;
    private final AvatarRepository avatarRepository;
    private final ProfileRepository profileRepository;
    private final TreeServiceImpl treeServiceImpl;
    private final FlowerServiceImpl flowerServiceImpl;
    private final RedisService redisService;
    private final ConstProperties constProperties;

    private static final String[] adj = {"기분 좋은", "열일하는", "보고싶은", "궁금한", "그리운", "노란", "빨간", "파란", "붉은", "푸른", "검은", "호기심 많은", "생각 없는", "생각 많은", "가냘픈", "고운", "가엾은", "뽀얀", "아름다운", "못난", "딱한", "희망찬", "메마른", "무서운", "밝은", "반가운", "보람찬", "멋진", "특별한", "섹시한", "기괴한", "가난한", "등이 가려운", "하얀", "가벼운", "무거운", "까탈스러운", "가혹한", "짭짤한", "고소한", "간사한", "간지러운", "길쭉한", "감격스러운", "장난스러운", "짓궂은", "사나운", "강건한", "강경한", "강렬한", "강력한", "감동한", "용맹한", "게으른", "부지런한", "개운한", "흥미로운", "거하게 취한", "거만한", "거무튀튀한", "거북한", "사악한", "쿨한", "터프한", "건방진", "건조한", "촉촉한", "바삭한", "심란한", "활기한", "격렬한", "결백한", "청렴한", "겸손한", "겸허한", "연애하는", "썸타는", "부끄러운", "수줍은", "쑥스러운", "민첩한", "재빠른", "돈 많은", "고급스러운", "고결한", "고급진", "고마운", "조그만", "씁쓸한", "고약한", "우아한", "딱딱한", "부드러운", "따뜻한", "차가운", "미지근한", "고뇌하는", "생각하는", "잠든", "꿀잠자는", "곤욕스러운", "돈독한", "튼튼한", "건강한", "운동하는", "곰살맞은", "친절한", "배려 깊은", "조용한", "공손한", "과감한", "너그러운", "웃음 많은", "까다로운", "야릇한", "음흉한", "감탄하는", "간식 먹는", "밥 먹는", "무뚝뚝한", "요염한", "교활한", "홀가분한", "구부정한", "구슬픈", "평안한", "굵직한", "날씬한", "뚱뚱한", "흡족한", "만족한", "감동 받은", "흥분한", "편안한", "용기를 얻은", "침착한", "협력적인", "열중한", "오싹한", "호기심 있는", "어리둥절한", "기운찬", "확신에 찬", "반항적인", "편견 없는", "단호한", "욕심 없는", "무아지경인", "황홀한", "매혹적인", "창의적인", "세심한", "시니컬한", "불타오르는", "신성한", "유쾌한", "당황하는", "진회의", "나령의", "성은의", "수빈의", "선민의", "규진의", "집착하는", "신난", "춤추는", "배부른", "나른한", "쾌변한", "신중한", "등이 가려운", "모기 물린", "의문스러운", "굉장한"};
    private static final String[] names = {"돼지", "코끼리", "진회", "나령", "성은", "수빈", "선민", "규진", "토끼", "호랑이", "용", "뱀", "찬국", "영진", "하영", "하은", "요셉", "현우", "코뿔소", "명균", "고양이", "강아지", "교수", "학생", "선생님", "꽃", "나무", "일기", "정원", "친구", "오소리", "고슴도치", "햄스터", "기니피그", "다람쥐", "날다람쥐", "사자", "표범", "치타", "하이에나", "기린", "하마", "악어", "펭귄", "부엉이", "올빼미", "곰", "소", "닭", "독수리", "타조", "고릴라", "오랑우탄", "침팬지", "원숭이", "코알라", "캥거루", "고래", "상어", "칠면조", "직박구리", "쥐", "청설모", "메추라기", "앵무새", "삵", "스라소니", "판다", "오소리", "오리", "거위", "백조", "두루미", "두더지", "우파루파", "맹꽁이", "너구리", "개구리", "두꺼비", "카멜레온", "이구나아", "노루", "제비", "까치", "고라니", "수달", "당나귀", "순록", "염소", "공작", "바다표범살모사", "들소", "박쥐", "참새", "물개", "바다사자", "살모사", "구렁이", "얼룩말", "산양", "멧돼지", "카피바라", "도롱뇽", "북극곰", "퓨마", "미어캣", "코요테", "라마", "딱따구리", "기러기", "비둘기", "스컹크", "돌고래", "까마귀", "매", "낙타", "여우", "사슴", "늑대", "재규어", "알파카", "양", "담비", "수박", "오렌지", "사과", "딸기", "당근", "참외", "메론", "복숭아", "리치", "포도", "레몬", "키위", "바나나", "라임", "체리", "드라큘라", "강시", "마스크", "안경", "하의", "상의", "치마", "바지", "양말", "신발", "부츠", "머리띠", "반달가슴곰", "귀걸이", "코걸이", "목걸이", "귀찌", "영양", "발찌", "발가락", "반지", "팬던트", "바위너구리", "하늘다람쥐", "뚱뚱꼬리저빌", "진돗개", "땃쥐", "줄무늬스컹크", "점박이물범", "치와와", "늘보주머니쥐", "네발가락고슴도치", "사원", "늘보곰", "느림보늘보원숭이", "마우스", "노란머리큰박쥐", "노란눈썹펭귄", "남방바다사자", "작은쥐여우원숭이", "족제비", "친칠라", "돼지사슴", "손가락", "가면팜사향고양이", "가시두더지", "개코원숭이", "꿀먹이박쥐", "나무늘보", "고모도왕도마뱀", "고무줄", "머리끈", "허리띠", "맹수", "하마", "향유고래", "혹등고래", "흰목도리여우원숭이", "피자", "치킨", "통닭", "젠킨스", "유니티", "이광수", "수빙수"};

    @Transactional
    @Override
    public User checkSignUp(String kakaoId) {

        User user = userRepository.findByKakaoId(kakaoId).orElse(null);

        // 회원 가입인 경우
        if (user == null) {
            String nickname = this.generateNickname();
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .role(Role.USER)
                    .build();
            User savedUser = userRepository.save(newUser);
            Avatar avatar = avatarRepository.findById(3).orElseThrow();

            MyAvatar myAvatar = MyAvatar.builder()
                    .user(savedUser)
                    .avatar(avatar)
                    .build();
            myAvatarRepository.save(myAvatar);

            Profile newProfile = Profile.builder()
                    .user(savedUser)
                    .nickname(nickname)
                    .myAvatar(myAvatar)
                    .build();
            profileRepository.save(newProfile);

            Tree tree = treeServiceImpl.randomTree();
            Flower flower = flowerServiceImpl.randomFlower();
            MyTree myTree = MyTree.builder()
                    .tree(tree)
                    .user(savedUser)
                    .build();
            MyTree savedMyTree = myTreeRepository.save(myTree);
            MyFlower myFlower = MyFlower.builder()
                    .user(savedUser)
                    .flower(flower)
                    .build();
            MyFlower savedMyFlower = myFlowerRepository.save(myFlower);
            GrowingPlant growingPlant = GrowingPlant.builder()
                    .myFlower(savedMyFlower)
                    .user(savedUser)
                    .diaryDays(0)
                    .answerCnt(0)
                    .answerDays(0)
                    .myTree(savedMyTree)
                    .build();
            growingPlantRepository.save(growingPlant);

            redisService.setValue(savedUser.getKakaoId() + "flowerExp", "0");
            redisService.setValue(savedUser.getKakaoId() + "treeExp", "0");
            int num = (int)(Math.random()*constProperties.getQuestionSize() + 1);
            redisService.setValue(savedUser.getKakaoId(), num + "");

            return savedUser;
        }

        return user;
    }

    private String generateNickname() {
        Profile tmp = null;
        String nickname = "";
        int adjNum = (int) (Math.random() * adj.length);
        int namesNum = (int) (Math.random() * names.length);
        int n = 0;

        do {
            n++;
            nickname = adj[adjNum] + " " + names[namesNum];
            tmp = profileRepository.findByNickname(nickname).orElse(null);
        } while (tmp != null && n < 1000);

        if (n >= 1000) {
            do {
                nickname = nickname + (int) (Math.random() * 100 + 1);
                tmp = profileRepository.findByNickname(nickname).orElse(null);
            } while (tmp != null);
        }

        return nickname;
    }

}