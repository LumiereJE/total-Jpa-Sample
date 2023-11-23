package com.kh.totalJpaSample.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

// 영속성. DB를 한번쓰고 날리는지, 재사용을 하는지
@Entity
// 이름을 안붙여도 되는데, 붙이는게 관례임
// 대소문자 구분을 안하기 때문에, 미리 이름을 지정 해주는 거임. 카멜표기법 안됨. _언더바 써야 함.
@Table(name= "member")
@Getter @Setter @ToString
public class Member {
    @Id
//    프라이머리 키 역할을 함. 테이블의 주인
    @GeneratedValue(strategy = GenerationType.AUTO)
//    AUTO : 스프링부트
//    identity : DB 종속
    @Column(name = "member_id")
    private Long id;

    private String userId;

    @Column(nullable = false)   // NULL을 허용하지 않음
    private String name;
    private String password;

    @Column(unique = true)      // DB내에 유일해야하는 값이라 제약조건을 건것임
    private String email;

    private LocalDateTime regDate;  // 얘 이름 join으로 했다간 mySQL에서 오류남.. 이름 바꿔줌 regDate로..

    // 데이터가 들어올 때 움직임
    @PrePersist
    public void prePersist() {  // DB에서 넣기전에 먼저 JAVA에서 날짜를 계산해서 DB에 넣어주겠다
        regDate = LocalDateTime.now();
    }
}
