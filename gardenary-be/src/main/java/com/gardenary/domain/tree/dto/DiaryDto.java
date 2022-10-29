package com.gardenary.domain.tree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDto {

    private int id;

    private UUID userId;

    private int treeId;

    private int myTreeId;

    private String createAt;

    private String content;

    private String diaryDate;
}
