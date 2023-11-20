package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 스프링 컨테이너한테 빈을 만들어달라고 요청함.
// DB를 읽고 쓰는 repository는 한개만 있어야 함.
@Repository  // 어노테이션으로 선언하면 싱글톤으로 등록 됨
// 네이밍 규칙에 의해서 API를 작성하면, 그에 맞는 쿼리문을 하이버네이트가 구현 해줌
// 인터페이스 문법은 구현부가 있으면 안됨. 완전 추상적인 곳임
// Hibernate: JPA의 구현부를 만들어줌
public interface MemberRepository  extends JpaRepository<Member, Long> {
    // optional : NULL 값이 못오게 함
    Optional <Member> findByEmail(String email);   // email로 멤버 객체 찾아달라는 쿼리문 작성한것임
    Member findByPassword(String pwd);
    Member findByEmailAndPassword(String email, String pwd);
    boolean existsByEmail(String email);
}
