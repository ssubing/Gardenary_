package com.gardenary.domain.current.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowingPlantResponseDto {

    private int flowerExp;
    private int treeExp;
    private boolean isWrite;
    private String question;
    private int questionNum;
    private int flowerDays;
    private int treeDays;

}
