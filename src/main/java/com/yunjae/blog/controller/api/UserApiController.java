package com.yunjae.blog.controller.api;

import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.config.auth.PrincipalDetailService;
import com.yunjae.blog.dto.ResponseDto;
import com.yunjae.blog.dto.joinUserDto;
import com.yunjae.blog.dto.updateUserDto;
import com.yunjae.blog.handler.exception.CustomValidationApiException;
import com.yunjae.blog.handler.exception.CustomValidationException;
import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.repository.UserRepository;
import com.yunjae.blog.service.UserService;
import com.yunjae.blog.validation.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController implements ValidationProperties {

    private final UserService userService;

    private final UserRepository userRepository;

    private final PrincipalDetailService principalDetailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/join")
    public String join(@Valid joinUserDto dto, BindingResult bindingResult) {
        // System.out.println("UserApiController: join 호출됨.");

        // Validation
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error: bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("Validation Failed", errorMap);
        } else {
            User user = dto.toEntity();
            userService.join(user); // 회원가입 (Transaction)
            return "/auth/loginForm";
        }
    }

    @PutMapping("/api/user/updateForm")
    public ResponseDto<Integer> update(@Valid updateUserDto dto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principal) {
        User user;
        try {
            user = userRepository.getOne(dto.getId());
        } catch(EntityNotFoundException E) {
            return new ResponseDto<Integer>(HttpStatus.RESET_CONTENT.value(), 1);
        }

        // if given password does not match the original password
        if (!bCryptPasswordEncoder.matches(dto.getPrevPassword(), user.getPassword()))
            return new ResponseDto<Integer>(HttpStatus.RESET_CONTENT.value(), 1);

        // Validation
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error: bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("Validation Failed", errorMap);
        } else {
            user.setPassword(dto.getNewPassword());

        /*if (dto.getEmail() != null)
            user.setEmail(dto.getEmail());*/
            // TODO: 인증 & 업데이트 기능 추가 시

            userService.update(user);
            // 여기부터 트랜잭션이 종료된 상태이기 때문에 새로운 값은 DB에 반영
            // 세션 값은 변경되지 않은 상태이기 때문에 직접 세션 값 변경 필요 -> Service에서

            // 인증 생성
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Authentication 생성(변경된 DB값으로) 및 변경
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Session 정보 수정
            principal.setUser(user);

            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }
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
