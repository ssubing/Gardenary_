package com.gardenary.domain.tree.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryListResponseDto {

    private List<DiaryResponseDto> diaryList;

    private String assetId;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
