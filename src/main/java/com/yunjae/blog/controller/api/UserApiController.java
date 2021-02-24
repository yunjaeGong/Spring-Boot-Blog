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

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController: save 호출됨.");
        user.setRole(UserRoleType.USER);
        int result = userService.join(user); // 회원가입 (Transaction)
        return new ResponseDto<Integer>(HttpStatus.OK, result); // Java Object를 Json으로 변환해 반환
    }
}
