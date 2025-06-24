package com.example.prj1.member.service;

import com.example.prj1.board.repository.BoardRepository;
import com.example.prj1.member.dto.MemberDto;
import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.dto.MemberListInfo;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
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
    private final BoardRepository boardRepository;

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

    public MemberDto get(String id) {
        Member member = memberRepository.findById(id).get();

        MemberDto dto = new MemberDto();
        dto.setId(id);
        dto.setNickName(member.getNickName());
        dto.setInfo(member.getInfo());
        dto.setCreateAt(member.getCreatedAt());

        return dto;
    }

    public boolean remove(MemberForm data, MemberDto user) {
        if (user != null) {
            Member member = memberRepository.findById(data.getId()).get();
            if (member.getId().equals(user.getId())) {
                String dbPw = member.getPassword();
                String formPw = data.getPassword();
                if (dbPw.equals(formPw)) {
                    // 탈퇴 처리 전 작성한 글 삭제
                    boardRepository.deleteByWriter(member);

                    // 회원 탈퇴
                    memberRepository.delete(member);

                    return true;
                }
            }
        }
        return false;
    }

    public boolean update(MemberForm data, MemberDto user, HttpSession session) {
        if (user != null) {

            Member member = memberRepository.findById(data.getId()).get();
            if (member.getId().equals(user.getId())) {
                String dbPw = member.getPassword();
                String formPw = data.getPassword();

                if (dbPw.equals(formPw)) {

                    member.setNickName(data.getNickName());
                    member.setInfo(data.getInfo());

                    memberRepository.save(member);
                    // TODO nav 닉네임 반영

                    return true;
                }
            }
        }
        return false;
    }

    public boolean updatePassword(String id, String oldPassword, String newPassword) {
        Member db = memberRepository.findById(id).get();

        String dbPw = db.getPassword();
        if (dbPw.equals(oldPassword)) {
            db.setPassword(newPassword);
            memberRepository.save(db);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String id, String password, HttpSession session) {
        Optional<Member> db = memberRepository.findById(id);
        if (db.isPresent()) {
            String dbPassword = db.get().getPassword();
            if (dbPassword.equals(password)) {
                MemberDto dto = new MemberDto();
                dto.setId(id);
                dto.setNickName(db.get().getNickName());
                dto.setInfo(db.get().getInfo());
                dto.setCreateAt(db.get().getCreatedAt());

                session.setAttribute("loggedInUser", dto);

                return true;
            }
        }

        return false;
    }
}
