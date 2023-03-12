package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class LoginServiceTest {


    @Autowired
    LoginService loginService;

    @Test
    public void 로그인테스트(){
        long id = 1;
        Member loginMember = loginService.login(id, "1");
        log.info(loginMember.getMemberId()+"");
        log.info(loginMember.toString());
    }

    @Test
    public void 로그인비밀번호_틀린경우(){
        long id =1;
        Member loginMember = loginService.login(id, "123");
        Assert.assertEquals(loginMember, null);
    }
}