package com.yunjae.blog.service;

import com.yunjae.blog.model.NestedReply;
import com.yunjae.blog.repository.NestedReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    NestedReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public List<NestedReply> getRootReplies(int boardId) {
        return replyRepository.findAllByBoardIdAndDepth(boardId, 0);
    }

    @Transactional(readOnly = true)
    public List<NestedReply> getNestedReplies(int boardId) {
        return replyRepository.findAllByBoardIdAndDepth(boardId, 1);
    }

    @Transactional
    public void deleteReply(int id) {
        replyRepository.deleteById(id);
    }
}
