package com.kh.totalJpaSample.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id;

    // 아이템은 한개인데, 여러군데 담길 수 있음
    @ManyToOne  // N:1 관계 → 하나의 장바구니에는 여러개의 상품을 담을 수 있음. cart입장에서 N:1임.
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 아이템이 한개인데, 여러개 담길 수 있음 ?인가?
    @ManyToOne  // N:1 관계 → 하나의 아이템은 여러 장바구니에 상품으로 담길 수 있음. 1쪽이 생성 되어있어야 N쪽이 생성될 수 있음
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;


}
