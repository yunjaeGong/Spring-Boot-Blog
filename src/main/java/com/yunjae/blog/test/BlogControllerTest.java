package com.yunjae.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 스프링이 com.yunjae.blog 이하를 스캔, 특정 어노테이션이 붙은 클래스 파일들을 new 해 IoC 컨테이너에서 관리
public class BlogControllerTest {
    @GetMapping("/test/hello")
    public String hello() {
        return "<h1>Hello Spring Boot!</h1>";
    }
}
