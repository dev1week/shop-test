package com.example.numble.timedeal.service;

import com.example.numble.timedeal.repository.MemberRepository;
import com.example.numble.timedeal.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(Long loginId, String password) {
        return memberRepository.findById(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
