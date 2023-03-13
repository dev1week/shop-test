package com.example.numble.timedeal.repository;

import com.example.numble.timedeal.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    Member findByMemberId(Long id);

}
