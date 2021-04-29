package com.yunjae.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NestedReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne // 여러 답글 > 게시글
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne // 여러 답글 > 유저
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = true)
    private int parentId;

    @Column(nullable = true)
    private int rootId;

    @Column(nullable = true)
    private int depth;

    // private int replyOrder; depth 등 존재하면

    @CreationTimestamp
    private Timestamp createDate;
}
