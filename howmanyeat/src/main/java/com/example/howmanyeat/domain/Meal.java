package com.example.howmanyeat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "meal")
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 즉시로딩하면 성능튜닝이 매우 어려워진다.
    // 성능 최적화가 꼭 필요한 경우엔 fetch join을 사용해라.
    // 지연로딩이면 new해서 멤버 객체를 가져오는게 아니라
    // (DB에서 가져오는게 아니라) 프록시 멤버 객체를 생성해서 넣어놓는다.
    // 멤버 객체를 실질적으로 사용할 때 SQL을 날려서 DB에서 조회를 한다.test
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty
    @Column(name = "food_name")
    private String foodName;

    @NotNull
    @Column(name = "calorie")
    private float calorie;

    @NotNull
    @Column(name = "meal_type")
    private int mealType;

    @Column(name = "memo")
    private String memo;

    @NotNull
    @Column(name = "meal_time")
    private LocalDateTime mealTime;

}
