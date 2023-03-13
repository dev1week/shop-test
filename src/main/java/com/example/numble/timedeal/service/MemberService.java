package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;


    //회원 가입
    @Transactional
    public Long join(Member member){
        //중복회원 검즘
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getMemberId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findByMemberId(Long memberId){
        return memberRepository.findByMemberId(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findByMemberId(id);
        member.setName(name);
    }

}
