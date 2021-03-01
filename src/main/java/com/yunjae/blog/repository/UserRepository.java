package com.yunjae.blog.repository;

import com.yunjae.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// DAO
// 자동으로 Bean 등록
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username=1?;
    Optional<User> findByUsername(String username);
}


// JPA Naming Strategy
// SELECT * FROM user WHERE username = ? AND Password = ? 쿼리 생성
// User findByUsernameAndPassword(String username, String password); // spring security 사용 x

//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND Password = ?2", nativeQuery = true)
//    User login(String username, String password);
// 위 방법과 동일