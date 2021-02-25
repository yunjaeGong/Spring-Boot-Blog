package com.yunjae.blog.service;

import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service // Component Scan을 통해 IoC Container에 등록
public class UserService { // 서비스: 하나 이상의 crud (송금)

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void join(User user) { // 회원가입 서비스
        userRepository.save(user);
        // GlobalExceptionHandler가 Exception 처리
    }

    @Transactional
    public void login(User user) { // 회원가입 서비스
        userRepository.login(user);
        // GlobalExceptionHandler가 Exception 처리
    }
}
