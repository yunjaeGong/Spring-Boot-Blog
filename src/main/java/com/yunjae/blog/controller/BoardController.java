package com.yunjae.blog.controller;

import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size = 2, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("boards", boardService.getPosts(pageable));
        // 컨트롤러에서 세션 정보를 어떻게 찾는지?
        // 반환된 블로그 post들이 index 페이지의 items="${boards}로 전달
        return "index"; // viewResolver 작동 /WEB-INF/views/index.jsp
    }

    // User 권한 필요
    @GetMapping("/board/writeForm")
    public String write() {
        return "board/writeForm";
    }
}
