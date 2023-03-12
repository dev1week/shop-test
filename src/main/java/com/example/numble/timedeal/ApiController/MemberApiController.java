package com.example.numble.timedeal.ApiController;

import com.example.numble.timedeal.Dto.CreateMemberRequest;
import com.example.numble.timedeal.Dto.CreateMemberResponse;
import com.example.numble.timedeal.Dto.UpdateMemberRequest;
import com.example.numble.timedeal.Dto.UpdateMemberResponse;
import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MemberApiController {
    @Autowired
    private final MemberService memberService;
    //회원 조회

    @GetMapping("/api/members")
    public Result findMembers(){
        List<Member> members =  memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(m-> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }
    //껍데기 오브젝트
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    //회원가입
    @PostMapping("/api/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setRoleType(request.getRoleType());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());

        //바로 update되자마자 id를 반환하도록 서비스를 짜는 것도 트래픽 관점에서 좋을 수 있겠다.

        Member findMember = memberService.findByMemberId(id);//해당 쿼리를 삭제하는 방향을 찾아보자.

        return new UpdateMemberResponse(findMember.getMemberId(), findMember.getName());
    }






}
