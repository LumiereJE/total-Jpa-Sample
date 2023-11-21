package com.kh.totalJpaSample.entity;


import com.kh.totalJpaSample.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@Entity                         // 클래스를 엔티티(클래스 문법)로 선언 🤍
@Table(name = "item")   // 엔티티와 매핑할 실제 생성된 테이블 이름 지정
// enum 만든 후 생성함
public class Item {
    @Id                                                 // 테이블의 기본 키 지정
    @Column(name = "item_id")                           // 필드(멤버)와 컬럼을 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)     // JPA 자동으로 생성, 전략을 결정
    private Long id;                                            // 상품 코드

    @Column(nullable = false, length = 50)              // NULL을 허용하지 않고 길이를 50자로 제한
    private String itemName;                                    // 상품 명

    @Column(name = "price", nullable = false)
    private int price;                                          // 상품 가격

    @Column(nullable = false)
    private int stockNumber;                                    // 재고 수량

    @Lob                                                // 영화 자막 등 엄청 긴 문자를 저장 할 때 사용
    @Column(nullable = false)
    private String itemDetail;                                     // 상품 상세 설명

    @Enumerated(EnumType.STRING)                        // enum으로 정의된 값을 문자열로 DB에 저장
    private ItemSellStatus itemSellStatus;


    private LocalDateTime regTime;                              // 등록 시간
    private LocalDateTime updateTime;                           // 수정 시간


}
