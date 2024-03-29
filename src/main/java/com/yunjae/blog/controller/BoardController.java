package com.yunjae.blog.controller;

import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.dto.ReplyDto;
import com.yunjae.blog.model.NestedReply;
import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import com.yunjae.blog.service.BoardService;
import com.yunjae.blog.service.ReplyService;
import com.yunjae.blog.service.UserService;
import com.yunjae.blog.util.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final ReplyService replyService;

    private final UserRepository userRepository;

    private final TimeAgo timeAgo;

    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size = 8, sort = "createDate", direction = Sort.Direction.ASC)Pageable pageable) {
        model.addAttribute("boards", boardService.getPosts(pageable));

        Map<String, Integer> configs = Map.of("maxPages",8); // TODO: json으로 전달 가능?
        model.addAllAttributes(configs);
        // 컨트롤러에서 세션 정보를 어떻게 찾는지?
        // 반환된 블로그 post들이 index 페이지의 items="${boards}로 전달
        return "index"; // viewResolver 작동 /WEB-INF/views/index.jsp
    }

    @GetMapping("/board/{boardId}")
    public String findById(@PathVariable int boardId, Model model) {
        model.addAttribute("board", boardService.getPost(boardId));

        List<ReplyDto> rootReplies, nestedReplies;

        rootReplies = replyService.getTimeAgoReplies(replyService.getRootReplies(boardId));
        nestedReplies = replyService.getTimeAgoReplies(replyService.getNestedReplies(boardId));

        model.addAttribute("rootReplies", rootReplies);
        model.addAttribute("nestedReplies", nestedReplies);

        // System.out.println("BoardController: findById - SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication().getName());

        return "board/detail";
    }

    // User 권한 필요
    @GetMapping("/board/writeForm")
    public String write() {
        return "board/writeForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) { // model은 해당 데이터를 가지고 view까지 이동
        model.addAttribute("board", boardService.getPost(id));
        return "board/updatePostForm";
    }
}
