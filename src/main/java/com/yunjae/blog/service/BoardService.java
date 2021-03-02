package com.yunjae.blog.service;

import com.yunjae.blog.model.Board;
import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.repository.BoardRepository;
import com.yunjae.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void save(Board board, User user) { // 글쓰기 서비스
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }
}
