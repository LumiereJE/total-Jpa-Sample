package com.kh.totalJpaSample.service;

import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service                // 해당 객체를 빈으로 등록해서 객체 생성없이 가능하게 함
@RequiredArgsConstructor // 매개변수가 전부 포함된 생성자를 자동으로 생성해줌

public class MemberService {
    private final MemberRepository memberRepository;    // 의존성 주입

    // 회원 등록
    public boolean saveMember(MemberDto memberDto) {
        // 가입여부 확인 (기존에 등록된 유저인지 확인하는 쿼리문)
        boolean isReg = memberRepository.existsByEmail(memberDto.getEmail());
        if(isReg) return false;  // 이미 가입되었으면 false로 넘겨줌. false가 아니면 아래로 이어짐 ( 객체에 저장 )

        Member member = new Member();       // 회원 만들어질때마다 만들어져야 하기 때문에 의존성 주입을 하지 않음
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        // PrePersist로 날짜는 DB들어갈때 자동 계산해서 넣게끔 해줬으므로 여기엔 넣지 않음.

        memberRepository.save(member);
        return true;
    }

    // 회원 전체 조회
    public List<MemberDto> getMemberList() {
        List<MemberDto> memberDtos = new ArrayList<>();
    // DB로부터 값을 가져오기, members에 한번에 담기
        List<Member> members = memberRepository.findAll();  // SELECT * ALL 이랑 같은거임
        for(Member member : members) { // member 객체 만큼 돌리기, 향상된 for문
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }

    // 회원 상세 조회
    public MemberDto getMemberDetail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        return convertEntityToDto(member);
    }

    // 페이지네이션 조회
    public List<MemberDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll(pageable).getContent();         // 지정한 페이지 만큼만 가져오게 함
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }

    // 전체 페이지 수 조회
    public int getMemberPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.findAll(pageable).getTotalPages();
    }


    // 회원 엔티티를 DTO로 변환하는 메서드 만들기 (일회성이 아니라 계속 쓸거니까)
    // public은 외부에서 호출할 일이 있을때 사용
    // 내부에서 사용하기만 하면 되면 private
    // memberDTO로 받아서 반환값으로
    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setName(member.getName());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}
