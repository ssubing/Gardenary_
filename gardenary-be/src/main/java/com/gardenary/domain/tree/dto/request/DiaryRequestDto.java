package com.gardenary.domain.tree.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryRequestDto {
    private String content;
    private LocalDateTime createdAt;
}
