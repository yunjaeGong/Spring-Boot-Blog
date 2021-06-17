package com.yunjae.blog.handler.exception;

import java.util.Map;

public class CustomValidationException extends RuntimeException {

    // JVM이 객체를 구분할 때 사용
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
