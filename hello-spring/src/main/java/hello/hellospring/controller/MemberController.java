package hello.hellospring.controller;

import hello.hellospring.dto.MemberJoinDTO;
import hello.hellospring.dto.SignInRequestDto;
import hello.hellospring.dto.SignInResultDto;
import hello.hellospring.service.MemberService;
import hello.hellospring.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") //CORS ERROR 해결
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String createForm() {
        return "members/createMemberForm";
    }   //문제

//    @PostMapping(value = "api/join", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> create(@RequestBody MemberJoinDTO memberJoinDTO) {
//        // 회원가입 로직 수행
//        String name = memberJoinDTO.getName();
//        String id = memberJoinDTO.getId();
//        String password = memberJoinDTO.getPwd();
//
//        Member member = new Member();
//
//        member.setPwd(password);
//        member.setId(id);
//        member.setName(name);
//        memberService.join(member);
//
//        String responseMessage = "회원가입이 성공적으로 완료되었습니다.";
//        return ResponseEntity.ok(responseMessage);
//    }
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public String join(@Valid @RequestBody MemberJoinDTO request) throws Exception{
        return memberService.join(request.toEntity());
    }

//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
//        return "members/login";
//    }


//    @PostMapping("/login")
//    public ResponseEntity<String> loginSuccess(@RequestBody Map<String, String> loginForm) {
//        String token = service.login(loginForm.get("id"), loginForm.get("password"));
//        return ResponseEntity.ok(token);
//    }

    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @PostMapping(value = "/login") //, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SignInResultDto signIn(@RequestBody SignInRequestDto signInRequestDto) throws RuntimeException, BaseException {

        log.info("[signIn] 로그인을 시도하고 있습니다. Email : {}, pw : ****", signInRequestDto.getUsername());
        SignInResultDto signInResultDto = memberService.signIn(signInRequestDto);

        if (signInResultDto.getCode() == 0) {
            log.info("[signIn] 정상적으로 로그인되었습니다. Email : {}, token : {}", signInRequestDto.getUsername(), signInResultDto.getToken());
        }

        System.out.println(signInResultDto.getToken());

        return signInResultDto;
    }

}

