package com.gardenary.domain.garden.service;

import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.garden.dto.GardenDto;
import com.gardenary.domain.garden.dto.GardenUserIdDto;
import com.gardenary.domain.garden.entity.Garden;
import com.gardenary.domain.garden.repository.GardenRepository;
import com.gardenary.domain.garden.dto.response.GardenFlowerResponseDto;
import com.gardenary.domain.garden.dto.response.GardenItemResponseDto;
import com.gardenary.domain.garden.dto.response.GardenListResponseDto;
import com.gardenary.domain.garden.dto.response.GardenTreeResponseDto;
import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.domain.item.repository.MyItemRepository;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.error.exception.FlowerApiException;
import com.gardenary.global.error.exception.ItemApiException;
import com.gardenary.global.error.exception.TreeApiException;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.FlowerErrorCode;
import com.gardenary.global.error.model.ItemErrorCode;
import com.gardenary.global.error.model.TreeErrorCode;
import com.gardenary.global.error.model.UserErrorCode;
import com.gardenary.global.properties.EncryptProperties;
import com.gardenary.global.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GardenServiceImpl implements GardenService{

    private final UserRepository userRepository;
    private final GardenRepository gardenRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final MyTreeRepository myTreeRepository;
    private final MyItemRepository myItemRepository;
    private final EncryptProperties encryptProperties;
    @Override
    public GardenListResponseDto getGardenInfo(User user, GardenUserIdDto gardenUserIdDto) {
        String encryptUserId = gardenUserIdDto.getUserId();
        if(encryptUserId == null) {
            return null;
        }
        String decryptUserId = null;
        try {
            decryptUserId = Encrypt.decryptAES256(encryptProperties.getEncryptKey(), encryptProperties.getEncryptIv(), encryptUserId);
        } catch (Exception e) {
            return null;
        }
        //유저 찾기, 오류 체크까지
        User GardenUser = userRepository.findById(UUID.fromString(decryptUserId)).orElseThrow(() -> new UserApiException(UserErrorCode.USER_NOT_FOUND));
        //해당 유저의 정원 정보 리스트 조회, 3종류의 리스트 생성
        List<Garden> gardenList = gardenRepository.findAllByUser(GardenUser);
        List<GardenFlowerResponseDto> flowerDtoList = new ArrayList<>();
        List<GardenTreeResponseDto> treeDtoList = new ArrayList<>();
        List<GardenItemResponseDto> itemDtoList = new ArrayList<>();
        //리스트 돌면서 type에 따라 myFlower/myTree/myItem통해서 Flower/Tree/Item assetId 찾기 (이 과정에서 각각 있는지 없는지 오류 체크)
        for (Garden garden : gardenList){
            if(garden.getType() == 1){
                MyFlower myFlower = myFlowerRepository.findById(garden.getObjectId()).orElseThrow(()->new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND));
                int myFlowerId = -1;
                if(GardenUser.getKakaoId().equals(user.getKakaoId())){
                    myFlowerId = myFlower.getId();
                }
                GardenFlowerResponseDto gardenFlowerResponseDto = GardenFlowerResponseDto.builder()
                        .start(myFlower.getCreatedAt())
                        .end(myFlower.getDoneAt())
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myFlowerId(myFlowerId)
                        .assetId(myFlower.getFlower().getAssetId())
                        .build();
                flowerDtoList.add(gardenFlowerResponseDto);
            } else if (garden.getType() == 2) {
                MyTree myTree = myTreeRepository.findById(garden.getObjectId()).orElseThrow(() -> new TreeApiException(TreeErrorCode.MY_TREE_NOT_FOUND));
                int myTreeId = -1;
                if(GardenUser.getKakaoId().equals(user.getKakaoId())){
                    myTreeId = myTree.getId();
                }
                GardenTreeResponseDto gardenTreeResponseDto = GardenTreeResponseDto.builder()
                        .start(myTree.getCreatedAt())
                        .end(myTree.getDoneAt())
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myTreeId(myTreeId)
                        .assetId(myTree.getTree().getAssetId())
                        .build();
                treeDtoList.add(gardenTreeResponseDto);
            } else if(garden.getType() == 3) {
                MyItem myItem = myItemRepository.findById(garden.getObjectId()).orElseThrow(()-> new ItemApiException(ItemErrorCode.ITEM_NOT_FOUND));
                int myItemId = -1;
                if(GardenUser.getKakaoId().equals(user.getKakaoId())){
                    myItemId = myItem.getId();
                }
                GardenItemResponseDto gardenItemResponseDto = GardenItemResponseDto.builder()
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myItemId(myItemId)
                        .assetId(myItem.getItem().getAssetId())
                        .build();
                itemDtoList.add(gardenItemResponseDto);
            }
        }
        return GardenListResponseDto.builder()
                .flowerList(flowerDtoList)
                .treeList(treeDtoList)
                .itemList(itemDtoList)
                .build();
    }

    @Override
    public boolean modifyGarden(User user, List<GardenDto> gardenDtoList) {
        //해당 유저의 기존 정원 배치
        List<Garden> tempList = gardenRepository.findAllByUser(user);
        //받은 dto리스트 entity로 빌드해 저장
        for (GardenDto gardenDto : gardenDtoList){
            //진짜 있는 object인지 확인
            int type = gardenDto.getType();
            if(type == 1) {
                MyFlower myFlower = myFlowerRepository.findById(gardenDto.getObjectId()).orElseThrow(()->new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND));
            }else if (type == 2) {
                MyTree myTree = myTreeRepository.findById(gardenDto.getObjectId()).orElseThrow(()->new TreeApiException(TreeErrorCode.MY_TREE_NOT_FOUND));
            } else if(type == 3) {
                MyItem myItem = myItemRepository.findById(gardenDto.getObjectId()).orElseThrow(()-> new ItemApiException(ItemErrorCode.ITEM_NOT_FOUND));
            }
            Garden garden = Garden.builder()
                    .user(user)
                    .objectId(gardenDto.getObjectId())
                    .x(gardenDto.getX())
                    .z(gardenDto.getZ())
                    .type(gardenDto.getType())
                    .build();
            gardenRepository.save(garden);
        }
        //데이터가 다 새로 저장이 되고 나면 기존 리스트 삭제
        for (Garden garden : tempList) {
            gardenRepository.deleteById(garden.getId());
        }
        return true;
    }
}
