package com.example.howmanyeat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "meal")
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty
    @Column(name = "food_name")
    private String foodName;

    @NotEmpty
    @Column(name = "calorie")
    private float calorie;

    @NotEmpty
    @Column(name = "meal_type")
    private int mealType;

    @Column(name = "memo")
    private String memo;

    @NotEmpty
    @Column(name = "meal_time")
    private LocalDateTime mealTime;

}
