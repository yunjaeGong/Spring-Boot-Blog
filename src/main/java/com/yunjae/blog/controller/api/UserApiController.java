package com.yunjae.blog.controller.api;

import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.dto.ResponseDto;
import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> join(@RequestBody User user) {
        // System.out.println("UserApiController: join 호출됨.");

        userService.join(user); // 회원가입 (Transaction)
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // Java Object를 Json으로 변환해 반환
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        userService.update(user);
        // 여기부터 트랜잭션이 종료된 상태이기 때문에 새로운 값은 DB에 반영
        // 세션 값은 변경되지 않은 상태이기 때문에 직접 세션 값 변경 필요
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
