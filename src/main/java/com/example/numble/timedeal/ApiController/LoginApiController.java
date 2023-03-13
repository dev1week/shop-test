package com.example.numble.timedeal.ApiController;

import com.example.numble.timedeal.constance.*;
import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.numble.timedeal.Dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

@Slf4j
@RestController
public class LoginApiController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request){
        log.info(Long.valueOf(loginRequest.getId())+" "+loginRequest.getPassword());

        Member loginMember = loginService.login(loginRequest.getId(), loginRequest.getPassword());

        log.info("login 시도함 {}", loginMember);

        if(loginMember==null){
            log.info("비밀번호가 유효하지 않습니다.");
            return new ResponseEntity<>("비밀번호나 id가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<Member> getUser(HttpServletRequest request){
        HttpSession session= request.getSession(false);

        //로그인을 먼저해야한다.
        if(session == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(loginMember, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }

        return "로그아웃되었습니다.";
    }
}
