package com.yunjae.blog.handler;

import com.yunjae.blog.dto.ResponseDto;
import com.yunjae.blog.dto.ValidationErrorRespDto;
import com.yunjae.blog.handler.exception.CustomValidationApiException;
import com.yunjae.blog.handler.exception.CustomValidationException;
import com.yunjae.blog.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    // 전역 exception e.g. 없는 페이지(404) 등

    @ExceptionHandler(value = IllegalArgumentException.class) // IllegalArgumentException이 발생하면 Spring이 아래 메소드로 Exception 전달
    public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
        System.out.println("handleArgumentException: " + e.getMessage());
        return new ResponseDto<>(-1, e.getMessage());
    };

    /*@ExceptionHandler(value = CustomValidationException.class) // IllegalArgumentException이 발생하면 Spring이 아래 메소드로 Exception 전달
    public ValidationErrorRespDto<> handleValidationException(CustomValidationException e) {
        System.out.println("handleValidationException");
        return new ValidationErrorRespDto<>(-1, e.getMessage(), e.getErrorMap());
    };*/

    @ExceptionHandler(value = CustomValidationException.class) // IllegalArgumentException이 발생하면 Spring이 아래 메소드로 Exception 전달
    public String handleValidationException(CustomValidationException e) {
        System.out.println("handleValidationException");
        // 오류를 alert로 보이고, history.back()을 통해 화면 넘어감(error page)을 방지
        // client(browser) 응답에는 script가 좋음
        // ajax, android(개발자) 통신에는 DTO 처리가 편리.
        return Script.back(e.getErrorMap().toString());
    };

    @ExceptionHandler(value = CustomValidationApiException.class) // IllegalArgumentException이 발생하면 Spring이 아래 메소드로 Exception 전달
    public ValidationErrorRespDto<Map<String, String>> handleValidationApiException(CustomValidationApiException e) {
        System.out.println("handleValidationApiException");
        // 오류를 alert로 보이고, history.back()을 통해 화면 넘어감(error page)을 방지
        // client(browser) 응답에는 script가 좋음
        // ajax, android(개발자) 통신에는 DTO 처리가 편리.
        return new ValidationErrorRespDto<>(-1, e.getMessage(), e.getErrorMap());
    };

}
