package com.yunjae.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {
    private String userId;
    private int boardId;
    private String content;
    private int parentId;
    private int depth;
    private int rootId;
}
