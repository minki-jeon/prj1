package com.example.prj1.member.service;

import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberForm data) {
        // Entity
        Member member = new Member();
        // Entity-Set
        member.setId(data.getId());
        member.setPassword(data.getPassword());
        member.setNickName(data.getNickName());
        member.setInfo(data.getInfo());
        // save
        memberRepository.save(member);

    }
}
