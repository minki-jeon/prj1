package com.example.prj1.repository;

import com.example.prj1.dto.BoardListInfo;
import com.example.prj1.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 전체 목록 조회
    List<BoardListInfo> findAllBy();

    // 전체 목록 조회 페이징
//    List<BoardListInfo> findAllBy(Pageable pageable);
    Page<BoardListInfo> findAllBy(Pageable pageable);
}