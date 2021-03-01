package com.yunjae.blog.controller;

import com.yunjae.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping({"", "/"})
    public String index() {
        // /WEB-INF/views/index.jsp로 찾아감
        // 세션 정보
        return "index";
    }

    @GetMapping("/board/writeForm")
    public String write() {
        return "board/writeForm";
    }
}
