package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Cart;
import com.kh.totalJpaSample.entity.Member;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

// 장바구니와 회원엔티티 1:1관계

@SpringBootTest     // Spring Context를 로드하여 테스트 환경 설정
@Transactional      // 트랜젝션, 데이터베이스의 논리적인 작업단위, 모두 성공이 아니면 롤백.
@Slf4j              //  로깅 데이터를 처리하기 위해 사용 → 로그를 남기기 위해서 사용함. 뭐하다가 오류가 났는지 체크. 로그 데이터를 쭉 저장함. 롬복에서 제공
@RequiredArgsConstructor    // Slf4j를 추가하면 이게 사용 가능해짐 → @Autowired를 안써도 됨 → 안썼다가 오류남..
@TestPropertySource(locations = "classpath:application-test.properties")

class CartRepositoryTest {      // 트랜젝션이 걸려있어도 test는 성공시에도 무조건 롤백을 함(콘솔:Rolled back transaction for test)
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext         // JPA의 EntityManager를 주입 받음
    EntityManager em;

    // 1:1 관계에선 무조건 엔티티가 있어야 하니까 생성해 줌
    // 회원 엔티티 생성
    public Member createMemberInfo() {
        Member member = new Member();
        member.setUserId("jks2024");
        member.setPassword("sphb8250");
        member.setName("곰돌이사육사");
        member.setEmail("jks2024@gmail.com");
        member.setRegDate(LocalDateTime.now());
        return member;
    }
    @Test
    @DisplayName("장바구니 회원 매핑 테스트")
    public void findCartAndMemberTest() {
        Member member = createMemberInfo();     // 위에서 생성한 회원 정보 가져옴
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setCartName("마켓컬리 장바구니");
        cart.setMember(member);
        cartRepository.save(cart);

        // 원래 자동으로 엔티티가 해주는데, 무언가 작업이 끝나자마자 급히 기록을 남겨야 하는 경우엔 이렇게 강제 사용 함
        em.flush();                 // 영속성 컨텍스트에 데이터 저장 후 트랜잭션이 끝날 때 데이터베이스에 기록함
        em.clear();

        Cart saveCart = cartRepository.findById(cart.getId()).orElseThrow(EntityExistsException::new);
        System.out.println(saveCart);
    }





}