package com.yunjae.blog.dto;

import com.yunjae.blog.model.Board;
import com.yunjae.blog.model.NestedReply;
import com.yunjae.blog.model.Reply;
import com.yunjae.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private int id;

    private String content;

    private User user;

    private int parentId;

    private int rootId;

    private int depth;

    private Timestamp createDate;

    private String timeAgo;

    public ReplyDto(NestedReply reply, String timeAgo) {
        id = reply.getId();
        content = reply.getContent();
        user = reply.getUser();
        parentId = reply.getParentId();
        rootId = reply.getRootId();
        depth = reply.getDepth();
        createDate = reply.getCreateDate();
        this.timeAgo = timeAgo;
    }
}
