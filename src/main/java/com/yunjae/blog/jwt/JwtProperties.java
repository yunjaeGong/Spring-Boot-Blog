package com.yunjae.blog.jwt;

public interface JwtProperties {
    String SECRET = "test"; // server의 private key
    int EXPIRATION_TIME = 60000*10; // 1/1000초 -> 10분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authentication";
}
