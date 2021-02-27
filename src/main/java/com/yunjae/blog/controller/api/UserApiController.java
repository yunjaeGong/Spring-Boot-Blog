package com.yunjae.blog.controller.api;

import com.yunjae.blog.dto.ResponseDto;
import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> join(@RequestBody User user) {
        System.out.println("UserApiController: join 호출됨.");
        user.setRole(UserRoleType.USER);
        userService.join(user); // 회원가입 (Transaction)
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Java Object를 Json으로 변환해 반환
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
    }*/


}
