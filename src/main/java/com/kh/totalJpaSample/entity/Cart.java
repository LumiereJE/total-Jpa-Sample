package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "cart")
@Getter @Setter
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cartName;

    @OneToOne   // 회원 엔티티와 1:1 매핑 (실제 있는 테이블과 JOIN걸어야 함)
    @JoinColumn(name = "member_id")     // 이름 선언하고, 멤버테이블에도 적용되어 있어야 함.
    private Member member;
}
