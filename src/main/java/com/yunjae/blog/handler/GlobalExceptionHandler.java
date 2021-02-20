package com.yunjae.blog.handler;

public class GlobalExceptionHandler {
    // 전역 exception e.g. 없는 페이지(404) 등

    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>" + e.getMessage() + "</h1>";
    };

}
