package com.gardenary.domain.current.entity;

import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "growing_plant")
public class GrowingPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(targetEntity = MyFlower.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "my_flower_id", nullable = false)
    private MyFlower myFlower;

    @ManyToOne(targetEntity = MyTree.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "my_tree_id", nullable = false)
    private MyTree myTree;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "answer_days", columnDefinition = "INT")
    private int answerDays;

    @Column(name = "diary_days", columnDefinition = "INT")
    private int diaryDays;

    @Column(name = "answer_cnt", columnDefinition = "INT")
    private int answerCnt;

    public void modifyMyFlower(MyFlower myFlower) {
        this.myFlower = myFlower;
    }
    public void modifyMyTree(MyTree myTree) { this.myTree = myTree; }
    public void modifyAnswerDays(int answerDays) {
        this.answerDays = answerDays;
    }

    public void modifyDiaryDays (int diaryDays) {
        this.diaryDays = diaryDays;
    }

    public void modifyAnswerCnt(int answerCnt) {
        this.answerCnt = answerCnt;
    }

}
