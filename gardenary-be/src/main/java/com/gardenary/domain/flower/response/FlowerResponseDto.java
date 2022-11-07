package com.gardenary.domain.flower.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowerResponseDto {
    private String name;
    private boolean isGet;
    private String meaning;
    private String bloom;
    private String content;
    private String assetId;
    private List<FlowerColorResponseDto> colorList;

    public void modifyColorList(List<FlowerColorResponseDto> colorList){
        this.colorList = colorList;
    }

    public void modifyIsGet(boolean isGet) {
        this.isGet = isGet;
    }
}
