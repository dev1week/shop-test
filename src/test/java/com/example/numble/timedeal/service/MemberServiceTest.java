package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        //when

        Long joinedId = memberService.join(member);
        //then

        Assert.assertEquals(member, memberRepository.findByMemberId(joinedId));
    }


    @Test(expected = IllegalStateException.class)
    public void 회원가입할때중복될경우예외발생() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);

        memberService.join(member2);
        fail("예외가 발생해야한다. ");
    }

}