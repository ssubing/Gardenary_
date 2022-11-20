package com.gardenary.domain.flower.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowerColorResponseDto {
    private String color;
    private boolean flag;

    public void modifyFlag(boolean flag) {
        this.flag = flag;
    }
}
