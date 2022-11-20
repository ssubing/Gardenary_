package com.gardenary.domain.garden.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenListResponseDto {

    private List<GardenFlowerResponseDto> flowerList;
    private List<GardenTreeResponseDto> treeList;
    private List<GardenItemResponseDto> itemList;

}
