package com.yunjae.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorRespDto<T> {
    private int status; // 1 성공, -1 실패
    private String message;
    private T data;
}
