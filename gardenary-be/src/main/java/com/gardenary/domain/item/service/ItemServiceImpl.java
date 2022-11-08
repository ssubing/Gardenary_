package com.gardenary.domain.item.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.garden.entity.Garden;
import com.gardenary.domain.garden.repository.GardenRepository;
import com.gardenary.domain.item.dto.response.*;
import com.gardenary.domain.item.entity.Item;
import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.domain.item.repository.ItemRepository;
import com.gardenary.domain.item.repository.MyItemRepository;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.ItemApiException;
import com.gardenary.global.error.model.ItemErrorCode;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final RedisService redisService;
    private final ConstProperties constProperties;
    private final ItemRepository itemRepository;
    private final MyTreeRepository myTreeRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final GardenRepository gardenRepository;
    private final GrowingPlantRepository growingPlantRepository;
    private final MyItemRepository myItemRepository;

    @Override
    public ItemResponseDto createItem(User user) {
        if (user == null) {
            return null;
        }

        Item item = new Item();
        boolean newItem = true;
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);

        if ((growingPlant.getDiaryDays() != 0 && growingPlant.getDiaryDays() % 3 == 0) || (growingPlant.getAnswerDays() != 0 && growingPlant.getAnswerDays() % 3 == 0)) {
            int num = (int) (Math.random() * constProperties.getItemSize() + 1);
            List<MyItem> myItems = myItemRepository.findAllByUser(user);
            for (MyItem myItem : myItems) {
                if (myItem.getItem().getId() == num) {
                    newItem = false;
                    break;
                }
            }

            item = itemRepository.findById(num).orElseThrow(() -> new ItemApiException(ItemErrorCode.ITEM_NOT_FOUND));
            MyItem myNewItem = MyItem.builder()
                    .item(item)
                    .user(user)
                    .build();
            MyItem savedMyItem = myItemRepository.save(myNewItem);
            if (savedMyItem.getId() == 0) {
                return null;
            }

        } else {
            return null;
        }

        return ItemResponseDto.builder()
                .itemAssetId(item.getAssetId())
                .newItem(newItem)
                .itemName(item.getName())
                .build();
    }

    @Override
    public InventoryResponseDto getInventory(User user) {
        if (user == null) {
            return null;
        }

        List<MyTree> totalMyTrees = myTreeRepository.findAllByUser(user);
        List<MyFlower> totalMyFlowers = myFlowerRepository.findAllByUser(user);
        List<MyItem> totalMyItems = myItemRepository.findAllByUser(user);
        List<Garden> gardens = gardenRepository.findAllByUser(user);

        Set<Integer> myTreeIdSet = new HashSet<>();
        Set<Integer> myFlowerIdSet = new HashSet<>();
        Set<Integer> myItemIdSet = new HashSet<>();

        List<InventoryTreeResponseDto> inventoryTreeResponseDtos = new ArrayList<>();
        List<InventoryFlowerResponseDto> inventoryFlowerResponseDtos = new ArrayList<>();
        List<InventoryItemResponseDto> inventoryItemResponseDtos = new ArrayList<>();

        for (Garden garden : gardens) {
            switch (garden.getType()) {
                case 2:
                    myTreeIdSet.add(garden.getObjectId());
                    break;
                case 1:
                    myFlowerIdSet.add(garden.getObjectId());
                    break;
                default:
                    myItemIdSet.add(garden.getObjectId());
                    break;
            }
        }
        boolean used;
        String period;
        for (MyTree myTree : totalMyTrees) {
            if (myTree.getDoneAt() == null) {
                continue;
            }
            used = false;
            if (myTreeIdSet.contains(myTree.getId())) {
                used = true;
            }
            period = myTree.getCreatedAt().toString().substring(0, 10) + "~" + myTree.getDoneAt().toString().substring(0, 10);
            inventoryTreeResponseDtos.add(InventoryTreeResponseDto.builder()
                    .assetId(myTree.getTree().getAssetId())
                    .name(myTree.getTree().getName())
                    .period(period)
                    .used(used)
                    .objectId(myTree.getId())
                    .build());
        }

        for (MyFlower myFlower : totalMyFlowers) {
            if (myFlower.getDoneAt() == null) {
                continue;
            }
            used = false;
            if (myFlowerIdSet.contains(myFlower.getId())) {
                used = true;
            }
            period = myFlower.getCreatedAt().toString().substring(0, 10) + "~" + myFlower.getDoneAt().toString().substring(0, 10);
            inventoryFlowerResponseDtos.add(InventoryFlowerResponseDto.builder()
                    .assetId(myFlower.getFlower().getAssetId())
                    .name(myFlower.getFlower().getName())
                    .period(period)
                    .used(used)
                    .objectId(myFlower.getId())
                    .build());
        }

        for (MyItem myItem : totalMyItems) {
            used = false;
            if (myItemIdSet.contains(myItem.getId())) {
                used = true;
            }
            inventoryItemResponseDtos.add(InventoryItemResponseDto.builder()
                    .assetId(myItem.getItem().getAssetId())
                    .name(myItem.getItem().getName())
                    .used(used)
                    .objectId(myItem.getId())
                    .build());
        }


        return InventoryResponseDto.builder()
                .trees(inventoryTreeResponseDtos)
                .flowers(inventoryFlowerResponseDtos)
                .items(inventoryItemResponseDtos)
                .build();
    }
}
