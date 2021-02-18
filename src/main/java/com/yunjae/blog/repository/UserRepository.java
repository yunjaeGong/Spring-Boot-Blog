package com.yunjae.blog.repository;

import com.yunjae.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 Bean 등록
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
