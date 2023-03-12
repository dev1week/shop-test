package com.example.numble.timedeal.ApiController;

import com.example.numble.timedeal.SessionUtil;
import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.domain.RoleType;
import com.example.numble.timedeal.service.LoginService;
import com.example.numble.timedeal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.numble.timedeal.Dto.LoginResponse;
import com.example.numble.timedeal.Dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import org.springframework.http.HttpStatus;


import java.util.StringTokenizer;

@Slf4j
@Controller
public class LoginApiController {
    final static String ADMIN_LOGIN_SUCCESS_MESSAGE = "관리자 로그인에 성공하였습니다";
    final static String USER_LOGIN_SUCCESS_MESSAGE = "사용자 로그인에 성공하였습니다";
    final static String TYPE_FAIL_MESSAGE = "권한 타입이 등록되어있지 않습니다.";
    final static String LOGIN_FAIL_MESSAGE = "로그인에 실패하였습니다.";

    @Autowired
    private MemberService memberService;

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, HttpSession session, HttpServletResponse response){
        log.info(Long.valueOf(request.getId())+" "+request.getPassword());

        Member loginMember = loginService.login(request.getId(), request.getPassword());

        if(loginMember==null){
            log.info("비밀번호가 유효하지 않습니다.");
            return new ResponseEntity<>("비밀번호나 id가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        Cookie loginCookie = new Cookie("id", String.valueOf(loginMember.getMemberId()));
        //Cookie loginCookie = new Cookie("id", String.valueOf("test"));
        response.addCookie(loginCookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<LoginResponse> getUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = null;
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("id")){
                    id = cookie.getValue();
                    break;
                }
            }
        }

        if(id==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("cookie Id : "+id);
        Member loginMember = memberService.findByMemberId(Long.valueOf(id));

        if(loginMember == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LoginResponse response = new LoginResponse(loginMember.getMemberId());

        return ResponseEntity.ok(response);
    }
}
