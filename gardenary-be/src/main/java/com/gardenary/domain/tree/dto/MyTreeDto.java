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
public class MyTreeDto {

    private int id;

    private int treeId;

    private UUID userId;

    private String createAt;
}
