package com.kh.totalJpaSample.controller;

import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.ListType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalJpaSample.utils.Common.CORS_ORIGIN;

@Slf4j  // Log f4 그렙 깐 거, 로그를 기록, 출력할 때 사용
@CrossOrigin(origins = CORS_ORIGIN) // util -> Common에서 갖고온것임
@RestController // 이거 빼면 DB연결 안됨
@RequestMapping("/users")
@RequiredArgsConstructor // 롬복문법임. 생성자 만들어주는 애임
public class MemberController {

    private final MemberService memberService;
    // 회원 등록
    @PostMapping("/new") // 서블릿이 받아서 /new를 불러줌
    // 응답이 Y,N면 되니까 Boolean, memberResister부분은 이름 맘대로 지으면 됨
    public ResponseEntity<Boolean> memberRegister(@RequestBody MemberDto memberDto) {
        // 제어역전, 의존성 주입의 현장임..!!
        boolean isTrue = memberService.saveMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 전체 조회
    @GetMapping("/list")    // <List<>>안에는 객체가 들어가야 함
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }

    // 회원 상세 조회

    @GetMapping("/detail/{email}")      // 패스 베리어블 방식
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }

}
