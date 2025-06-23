package com.example.prj1.member.service;

import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.dto.MemberListInfo;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberForm data) {
        // id 중복 체크
        Optional<Member> db = memberRepository.findById(data.getId());

        if (db.isEmpty()) {
            Optional<Member> byNickName = memberRepository.findByNickName(data.getNickName());
            if (byNickName.isEmpty()) {
                // Entity
                Member member = new Member();
                // Entity-Set
                member.setId(data.getId());
                member.setPassword(data.getPassword());
                member.setNickName(data.getNickName());
                member.setInfo(data.getInfo());
                // save
                memberRepository.save(member);
            } else {
                throw new DuplicateKeyException(data.getNickName() + "는 이미 존재하는 별명입니다.");
            }
        } else {
            throw new DuplicateKeyException(data.getId() + "는 이미 존재하는 아이디입니다.");
        }


    }

    public List<MemberListInfo> list() {
        return memberRepository.findAllBy();
    }
}
