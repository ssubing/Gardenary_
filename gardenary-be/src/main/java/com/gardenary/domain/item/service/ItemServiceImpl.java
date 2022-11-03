package com.gardenary.domain.item.service;

import com.gardenary.domain.item.dto.response.ItemResponseDto;
import com.gardenary.domain.item.entity.Item;
import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.domain.item.repository.ItemRepository;
import com.gardenary.domain.item.repository.MyItemRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.error.exception.ItemApiException;
import com.gardenary.global.error.model.ItemErrorCode;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final RedisService redisService;
    private final ConstProperties constProperties;
    private final ItemRepository itemRepository;
    private final MyItemRepository myItemRepository;

    @Override
    public ItemResponseDto createItem(User user) {
        int flowerExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId() + "flowerExp"));
        int treeExp = Integer.parseInt(redisService.getStringValue(user.getKakaoId() + "treeExp"));
        Item item = new Item();
        boolean newItem = true;

        if (flowerExp != 0 && flowerExp % 100 == 0 || treeExp != 0 && treeExp % 100 == 0) {
            // TODO : 아이템 데이터 넣고서 yml 아이템 사이즈 조절
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
}
