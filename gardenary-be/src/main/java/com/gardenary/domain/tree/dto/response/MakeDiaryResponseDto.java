package com.gardenary.domain.tree.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakeDiaryResponseDto {

    private int totalExp;

    private boolean isItem;
}
