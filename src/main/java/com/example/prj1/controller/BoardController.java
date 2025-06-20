package com.example.prj1.controller;

import com.example.prj1.dto.BoardForm;
import com.example.prj1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("write")
    public String writeView() {

        return "board/write";
    }

    @PostMapping("write")
    public String write(BoardForm data) {
        boardService.add(data);

        return "redirect:/board/write";
    }

    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       Model model) {

        var result = boardService.list(page);

//      model.addAttribute("boardList", result);
        model.addAllAttributes(result);

        return "board/list";
    }


    @GetMapping("view")
    public String view(Integer id, Model model) {

        var dto = boardService.get(id);

        model.addAttribute("board", dto);

        return "board/view";
    }
}
