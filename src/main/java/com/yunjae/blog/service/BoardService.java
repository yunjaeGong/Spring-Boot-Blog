package com.yunjae.blog.service;

import com.yunjae.blog.dto.ReplySaveRequestDto;
import com.yunjae.blog.model.*;
import com.yunjae.blog.repository.BoardRepository;
import com.yunjae.blog.repository.NestedReplyRepository;
import com.yunjae.blog.repository.ReplyRepository;
import com.yunjae.blog.repository.UserRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private ReplyRepository replyRepository;*/

    @Autowired
    private NestedReplyRepository replyRepository;

    @Transactional
    public void savePost(Board board, User user) { // 글쓰기 서비스
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getPosts(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board getPost(int id) {
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
        });
        // Board에는 List<Reply> 존재
    }

    @Transactional
    public void updatePost(int id, Board requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
        }); // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당함수(Service) 종료될 때 트랜잭션이 종료되고, Dirty Checking이 일어난다 -> 자동 업데이트
    }

    @Transactional
    public void deletePost(int id) {
        boardRepository.deleteById(id);
    }

}
