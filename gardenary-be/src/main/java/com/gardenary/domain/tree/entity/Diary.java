package com.gardenary.domain.tree.entity;

import com.gardenary.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate //실제 값이 변경된 컬럼으로만 update 쿼리를 만든듦
@DynamicInsert // null이 아닌 컬럼들만 포함하여 insert 쿼리를 만듦
@EntityListeners(AuditingEntityListener.class) // Auditing 기능: db 기록(수정일 등)에 자동으로 시간을 매핑하여 db의 테이블에 넣어줌
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 만듦
@Builder
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(targetEntity = Tree.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne(targetEntity = MyTree.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "my_tree_id", nullable = false)
    private MyTree myTree;

    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "content", columnDefinition = "VARCHAR(1000)")
    private String content;

    @Column(name = "diary_date", columnDefinition = "DATETIME")
    private LocalDateTime diaryDate;
}
