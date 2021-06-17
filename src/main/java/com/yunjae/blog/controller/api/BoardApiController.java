package com.yunjae.blog.controller.api;

import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.dto.ReplySaveRequestDto;
import com.yunjae.blog.dto.ResponseDto;
import com.yunjae.blog.model.Board;
import com.yunjae.blog.model.NestedReply;
import com.yunjae.blog.model.Reply;
import com.yunjae.blog.repository.NestedReplyRepository;
import com.yunjae.blog.service.BoardService;
import com.yunjae.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/api/board/save")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.savePost(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Java Object를 Json으로 변환해 반환
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable int id) {
        boardService.deletePost(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // DTO 사용하는게 좋다
    @PostMapping("/api/board/{boardId}/reply")
    // public ResponseDto<Integer> save(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
    public ResponseDto<Integer> saveReply(@RequestBody ReplySaveRequestDto replySaveRequestDto, @AuthenticationPrincipal PrincipalDetail principal) {

        replyService.saveReply(replySaveRequestDto);
        // sqlSave(requestBoard) - native query
        // System.out.println("BoardApiController: saveReply - SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Java Object를 Json으로 변환해 반환
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board requestBoard) {
        boardService.updatePost(id, requestBoard);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/{replyId}")
    public ResponseDto<Integer> deleteReply(@PathVariable int boardId, @PathVariable int replyId) {
        replyService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{boardId}/{replyId}")
    public ResponseDto<Integer> updateReply(@PathVariable int boardId, @PathVariable int replyId, @RequestBody NestedReply reply) {
        System.out.println("updateReply: " + reply.getContent());
        replyService.updateReply(replyId, reply.getContent());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /*@PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
        System.out.println("UserApiController: login 호출됨.");
        User principal = userService.login(user); // 로그인 (Transaction), Principal: 접근주체
        System.out.println(principal.toString()); // nullptr
        if (principal.getUsername() != null) {
            session.setAttribute("principal", principal); // 세션 생성
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Java Object를 Json으로 변환해 반환
    }*/ // 전통적인 방법(spring security 사용 x)


}
