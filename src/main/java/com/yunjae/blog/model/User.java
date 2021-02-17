package com.yunjae.blog.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


// ORM -> Object를 테이블로 매핑
@Entity // User 클래스가 자동으로 Mysql에 테이블이 생성
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감(sequence, table, auto 등 존재)
    private int id; // oracle: sequence, mysql: auto_increment

    @Column(nullable = false, length = 30)
    private String username; // 아이디

    @Column(nullable = false, length = 30)
    private String password; // hash로 암호화

    @Column(nullable = false, length = 40)
    private String email;

    @ColumnDefault("'user'")
    private String role;

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate; // 계정 생성 일자

    // private Timestamp updateDate; // 게정 정보 수정일자
}