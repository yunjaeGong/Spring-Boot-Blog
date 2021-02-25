package com.yunjae.blog.handler;

import com.yunjae.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    // 전역 exception e.g. 없는 페이지(404) 등

    @ExceptionHandler(value = IllegalArgumentException.class) // IllegalArgumentException이 발생하면 Spring이 아래 메소드로 Exception 전달
    public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
        System.out.println("handleArgumentException: " + e.getMessage());
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    };

}
