package com.example.howmanyeat.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty // 공백불가
    private String name;

//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

}
