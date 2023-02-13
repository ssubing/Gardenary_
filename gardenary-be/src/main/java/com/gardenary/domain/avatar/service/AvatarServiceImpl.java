package com.gardenary.domain.avatar.service;

import com.gardenary.domain.avatar.dto.response.AvatarListResponseDto;
import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.avatar.entity.Avatar;
import com.gardenary.domain.avatar.entity.MyAvatar;
import com.gardenary.domain.avatar.repository.AvatarRepository;
import com.gardenary.domain.avatar.repository.MyAvatarRepository;
import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.repository.FlowerRepository;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.tree.repository.TreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.properties.ConstProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService {


    private final AvatarRepository avatarRepository;
    private final MyAvatarRepository myAvatarRepository;
    private final MyTreeRepository myTreeRepository;
    private final TreeRepository treeRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final FlowerRepository flowerRepository;
    private final ConstProperties constProperties;

    @Override
    public AvatarListResponseDto getNewAvatar(User user, int type) {
        //사용자의 현재 아바타 정보 조회
        List<MyAvatar> myAvatarList = myAvatarRepository.findAllByUser(user);
        List<Avatar> avatarList = avatarRepository.findAll();
        //사용자가 있는 아바타의 경우에만 돌기
        List<Integer> haveAvatar = new ArrayList<>();
        for(MyAvatar myAvatar : myAvatarList) {
            haveAvatar.add(myAvatar.getAvatar().getId());
        }
        List<AvatarResponseDto> avatarResponseDtoList = new ArrayList<>();
        //꽃일 경우
        if(type == 1) {
            List<MyFlower> myFlowerList = myFlowerRepository.findAllByUser(user);
            myFlowerList.remove(myFlowerList.size()-1);
            if(!haveAvatar.contains(2) && get2Avatar(myFlowerList)) {
                listAddAndSave(1, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(5) && get5Avatar(myFlowerList)) {
                listAddAndSave(4, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(6) && get6Avatar(myFlowerList)) {
                listAddAndSave(5, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(7) && get7Avatar(myFlowerList)) {
                listAddAndSave(6, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(8) && get8Avatar(myFlowerList)) {
                listAddAndSave(7, avatarResponseDtoList, avatarList, user);
            }
        }

        //나무일 경우
        if(type == 2) {
            List<MyTree> myTreeList = myTreeRepository.findAllByUser(user);
            myTreeList.remove(myTreeList.size() - 1);
            if(!haveAvatar.contains(1) && get1Avatar(myTreeList)) {
                listAddAndSave(0, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(4) && get4Avatar(myTreeList)) {
                listAddAndSave(3, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(9) && get9Avatar(myTreeList)) {
                listAddAndSave(8, avatarResponseDtoList, avatarList, user);
            }
            if(!haveAvatar.contains(10) && get10Avatar(myTreeList)) {
                listAddAndSave(9, avatarResponseDtoList, avatarList, user);
            }
        }
        //획득한 아바타있는지 확인
        boolean flag = false;
        if(avatarResponseDtoList.size() > 0) {
            flag = true;
        }

        return AvatarListResponseDto.builder()
                .avatarResponseDtoList(avatarResponseDtoList)
                .flag(flag)
                .build();
    }

    public void listAddAndSave(int num, List<AvatarResponseDto> avatarResponseDtoList, List<Avatar> avatarList, User user) {
        avatarResponseDtoList.add(AvatarResponseDto.builder()
                .assetId(avatarList.get(num).getAssetId())
                .isAcquired(true)
                .build());
        myAvatarRepository.save(MyAvatar.builder()
                .avatar(avatarList.get(num))
                .user(user)
                .build());
    }

    //같은 종류 나무 3가지
    public boolean get1Avatar(List<MyTree> myTreeList) {
        HashMap<String, Integer> myTreeMap = new HashMap<>();
        for(MyTree myTree : myTreeList) {
            if(myTreeMap.containsKey(myTree.getTree().getName())) {
                myTreeMap.put(myTree.getTree().getName(), myTreeMap.get(myTree.getTree().getName()) + 1);
            } else {
                myTreeMap.put(myTree.getTree().getName(), 1);
            }
            if(myTreeMap.get(myTree.getTree().getName()) == 3) {
                return true;
            }
        }
        return false;
    }

    //모든 꽃
    public boolean get2Avatar(List<MyFlower> myFlowerList) {
        HashSet<String> myFlowerSet = new HashSet<>();
        for(MyFlower myFlower : myFlowerList) {
            myFlowerSet.add(myFlower.getFlower().getId());
        }
        return myFlowerSet.size() == constProperties.getFlowerSize();
    }

    //아무 나무 하나 성장 완료
    public boolean get4Avatar(List<MyTree> myTreeList) {
        return myTreeList.size() == 1;
    }

    //아무 꽃 하나 성장 완료
    public boolean get5Avatar(List<MyFlower> myFlowerList) {
        return myFlowerList.size() == 1;
    }
    
    //꽃 30 종류 성장
    public boolean get6Avatar(List<MyFlower> myFlowerList) {
        HashSet<String> myFlowerSet = new HashSet<>();
        for (MyFlower myFlower : myFlowerList) {
            myFlowerSet.add(myFlower.getFlower().getId());
        }
        if(myFlowerSet.size() == 30) {
            return true;
        }
        return false;
    }

    //서로 다른 꽃 5개 수집
    public boolean get7Avatar(List<MyFlower> myFlowerList) {
        HashSet<String> myFlowerSet = new HashSet<>();
        for(MyFlower myFlower : myFlowerList) {
            myFlowerSet.add(myFlower.getFlower().getName());
            if(myFlowerSet.size() == 5) {
                return true;
            }
        }
        return false;
    }

    //같은 꽃 3개
    public boolean get8Avatar(List<MyFlower> myFlowerList) {
        HashMap<String, Integer> myFlowerMap = new HashMap<>();
        for(MyFlower myFlower : myFlowerList) {
            if(myFlowerMap.containsKey(myFlower.getFlower().getName())) {
                myFlowerMap.put(myFlower.getFlower().getName(), myFlowerMap.get(myFlower.getFlower().getName()) + 1);
            } else {
                myFlowerMap.put(myFlower.getFlower().getName(), 1);
            }
            if(myFlowerMap.get(myFlower.getFlower().getName()) == 3) {
                return true;
            }
        }
        return false;
    }

    //서로 다른 나무 5개 수집
    public boolean get9Avatar(List<MyTree> myTreeList) {
        HashSet<String> myTreeSet = new HashSet<>();
        for (MyTree myTree : myTreeList) {
            myTreeSet.add(myTree.getTree().getName());
            if(myTreeSet.size() == 5) {
                return true;
            }
        }
        return false;
    }

    //모든 나무 수집
    public boolean get10Avatar(List<MyTree> myTreeList) {
        HashSet<String> myTreeSet = new HashSet<>();
        for(MyTree myTree : myTreeList) {
            myTreeSet.add(myTree.getTree().getName());
        }
        return myTreeSet.size() == constProperties.getTreeSize();
    }
}
