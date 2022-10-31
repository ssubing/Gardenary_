package com.gardenary.domain.flower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerCompleteDto {
    private int totalExp;
    private boolean isItem;

    public void updateIsItem(boolean isItem) {
        this.isItem = isItem;
    }
    public void updateTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }
}
