package com.yunjae.blog.model;

import com.yunjae.blog.dto.ReplySaveRequestDto;
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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 사용
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

    @CreationTimestamp
    private Timestamp createDate;

}


