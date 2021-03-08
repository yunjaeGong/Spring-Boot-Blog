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
        String encPassword = encoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole(UserRoleType.USER);
        userRepository.save(user);
        // GlobalExceptionHandler가 Exception 처리
    }

    @Transactional
    public void update(User updatedUser) {
        // 수정시에는 Persistent Context User 오브젝트를 영속화 시킨 후 영속화된 User 오브젝트를 수정
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update 실행
        System.out.println(updatedUser.getEmail());
        System.out.println(updatedUser.getId());
        User persistent = userRepository.findById(updatedUser.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("User Service: update, 회원 정보를 찾을 수 없습니다.");
        });
        String encPassword = encoder.encode(updatedUser.getPassword());
        persistent.setPassword(encPassword);
        persistent.setEmail(updatedUser.getEmail());

        // 회원 수정함수 종료시 : 서비스 종료 -> 트랜잭션 종료(Commit)
        // 영속화된 persistent 객체에 변화가 생기면 자동으로 update문을 날린다(dirty checking)
        // 이 때 DB 값은 변경되지만 session(principal)의 정보는 바뀌지 않은 상태
    }

    /*@Transactional(readOnly = true) // Select 시 Transaction 시작, 서비스 종료시 트랜잭션 종료 (정합성)
    public User login(User user) { // 로그인 서비스
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
