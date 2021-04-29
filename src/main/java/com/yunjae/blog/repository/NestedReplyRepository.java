package com.yunjae.blog.repository;

import com.yunjae.blog.model.NestedReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NestedReplyRepository extends JpaRepository<NestedReply, Integer> {
    List<NestedReply> findAllByBoardIdAndDepth(int boardId, int depth);
}
