package com.example.howmanyeat.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @NotEmpty // 공백불가
    @Column(name = "name")
    private String name;

    @Column(name = "height")
    private float height;

    @Column(name = "weight")
    private float weight;

    @Column(name = "muscle_mass")
    private float muscleMass;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member")
    private List<Meal> meals = new ArrayList<>();

}
