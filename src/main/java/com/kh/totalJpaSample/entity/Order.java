package com.kh.totalJpaSample.entity;

import com.kh.totalJpaSample.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders") // order가 DB 예약어로 걸려서 이름 바꿔줌
public class Order {
    @Id
    @GeneratedValue     // 원래 디폴트가 AUTO여서 이대로 적어도 됨.
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    // 1:N에서는 N이 주인공임. 연관관계의 주인이 아님을 표시함
    // 1:N → 장바구니에 담은 물건이 존재해야 의미가 있으므로 item들이 주인공임(many)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)   // 영속성 전이, 고아 객체 제거
    private List<OrderItem> orderItemList = new ArrayList<>(); // 장바구니에 담은 물건이 필수로 있어야 하기 때문에 만들어진거 가져옴


}
