package com.yunjae.blog.service;

import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // Component Scan을 통해 IoC Container에 등록
public class UserService { // 서비스: 하나 이상의 crud (송금)

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(User user) { // 회원가입 서비스
        String encPassword =encoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole(UserRoleType.USER);
        userRepository.save(user);
        // GlobalExceptionHandler가 Exception 처리
    }

    /*@Transactional(readOnly = true) // Select 시 Transaction 시작, 서비스 종료시 트랜잭션 종료 (정합성)
    public User login(User user) { // 로그인 서비스
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
