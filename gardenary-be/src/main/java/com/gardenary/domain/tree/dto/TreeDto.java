package com.gardenary.domain.tree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeDto {

    private int id;

    private String name;

    private String content;
}
