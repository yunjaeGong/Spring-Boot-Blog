package com.yunjae.blog.repository;

import com.yunjae.blog.dto.ReplySaveRequestDto;
import com.yunjae.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Transactional
    @Query(value = "INSERT INTO reply(userId, boardId, content) VALUES(?1, ?2, ?3)", nativeQuery = true)
    void sqlSave(ReplySaveRequestDto dto);
}
