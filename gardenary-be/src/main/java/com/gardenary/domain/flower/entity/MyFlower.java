package com.gardenary.domain.flower.entity;

import com.gardenary.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "my_flower")
public class MyFlower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(targetEntity = Flower.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id", nullable = false)
    private Flower flower;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    @CreatedDate
    private LocalDateTime createdAt;


}
