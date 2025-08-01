package com.example.prj1.board.repository;

import com.example.prj1.board.dto.BoardListInfo;
import com.example.prj1.board.entity.Board;
import com.example.prj1.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 전체 목록 조회
    List<BoardListInfo> findAllBy();

    // 전체 목록 조회 페이징
//    List<BoardListInfo> findAllBy(Pageable pageable);
    Page<BoardListInfo> findAllBy(Pageable pageable);


    @Query("""
            SELECT b FROM Board b 
            WHERE b.title LIKE :keyword 
                OR b.content LIKE :keyword 
                OR b.writer.nickName LIKE :keyword
            """)
    Page<BoardListInfo> searchByKeyword(String keyword, PageRequest id);

    void deleteByWriter(Member member);
}