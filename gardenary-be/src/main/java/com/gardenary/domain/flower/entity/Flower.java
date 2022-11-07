package com.gardenary.domain.flower.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "flower")
public class Flower {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "meaning", columnDefinition = "VARCHAR(255)")
    private String meaning;

    @Column(name = "bloom", columnDefinition = "VARCHAR(255)")
    private String bloom;

    @Column(name = "content", columnDefinition = "VARCHAR(255)")
    private String content;

    @Column(name = "color", columnDefinition = "VARCHAR(50)")
    private String color;

    @Column(name = "asset_id", columnDefinition = "VARCHAR(255)")
    private String assetId;

}
