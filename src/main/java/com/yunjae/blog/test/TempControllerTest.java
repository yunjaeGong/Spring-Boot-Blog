package com.yunjae.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Data가 아닌 파일 반환!
public class TempControllerTest {
    public static String TAG = "tempHome";

    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        return "home.html";
        // http://localhost:8000/blog/WEB-INF/views/home.html.jsp를 찾음
        // 파일리턴 기본경로 : src/main/resource/static
        // 리턴명: /home.html
        // 풀 경로: src/main/resource/static/home.html
        //TODO: application.yml에서 mvc 주석 해제
    }
}
