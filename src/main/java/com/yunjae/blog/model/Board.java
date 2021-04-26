package com.yunjae.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 사용
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // 다대 일 관계(게시글 > 유저), Board 테이블 선택하면 user 정보는 바로 join 해서 가져옴
    @JoinColumn(name = "userId") // 작성한 user의 Id 값, table의 칼럼(FK)로 생성
    private User user; // BD는 오프젝트 저장 불가. (원래는 FK 사용), 자바는 오브젝트를 저장할 수 있다.
    // sql에서는 userId integer로 생성

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 일대 다 관계(게시글 < 댓글), OneToMany 관계는 기본 fetch 전략이 LazyLoading(데이터가 많기때문)
    // 게시글 지우면 Cascade 삭제
    @JsonIgnoreProperties({"board"})
    @OrderBy("createDate asc")
    private List<NestedReply> replies; // mappedBy: Board 테이블의 FK가 아님. Reply 테이블의 FK를 참조

    @CreationTimestamp
    private Timestamp createDate;
}
