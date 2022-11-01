package com.gardenary.domain.flower.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerCompleteResponseDto {
    private int totalExp;
    private boolean isItem;

    public void updateIsItem(boolean isItem) {
        this.isItem = isItem;
    }
    public void updateTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }
}
