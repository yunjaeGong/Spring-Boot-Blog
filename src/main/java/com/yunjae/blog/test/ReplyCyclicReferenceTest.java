package com.yunjae.blog.test;

import com.yunjae.blog.model.Board;
import com.yunjae.blog.model.Reply;
import com.yunjae.blog.repository.BoardRepository;
import com.yunjae.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyCyclicReferenceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id) {
        return boardRepository.findById(id).get(); // jackson 라이브러리(Object를 json으로 반환) => 모델의 getter 호출
    }

    @GetMapping("/test/reply")
    public List<Reply> getReply() {
        return replyRepository.findAll();
    }
}
