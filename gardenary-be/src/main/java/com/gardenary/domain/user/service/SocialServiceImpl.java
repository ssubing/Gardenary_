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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

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

            redisService.setValue(savedUser.getKakaoId()+"flowerExp", "0");
            redisService.setValue(savedUser.getKakaoId()+"treeExp", "0");
            Random random = new Random(System.nanoTime());
            int num = random.nextInt(constProperties.getQuestionSize());
            redisService.setValue(savedUser.getKakaoId(), num + "");

            return savedUser;
        }

        return user;
    }

    private String generateNickname() {
        Profile tmp = null;
        String nickname = "";
        do {
            nickname = RandomStringUtils.randomNumeric(5);
            tmp = profileRepository.findByNickname(nickname).orElse(null);
        } while (tmp != null);
        return nickname;
    }
}