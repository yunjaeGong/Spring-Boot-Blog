package com.yunjae.blog.service;

import com.yunjae.blog.dto.ReplyDto;
import com.yunjae.blog.dto.ReplySaveRequestDto;
import com.yunjae.blog.model.Board;
import com.yunjae.blog.model.NestedReply;
import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.BoardRepository;
import com.yunjae.blog.repository.NestedReplyRepository;
import com.yunjae.blog.repository.UserRepository;
import com.yunjae.blog.util.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final NestedReplyRepository replyRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final TimeAgo timeAgoUtil;

    @Transactional(readOnly = true)
    public List<NestedReply> getRootReplies(int boardId) {
        return replyRepository.findAllByBoardIdAndDepth(boardId, 0);
    }

    @Transactional(readOnly = true)
    public List<NestedReply> getNestedReplies(int boardId) {
        return replyRepository.findAllByBoardIdAndDepth(boardId, 1);
    }

    @Transactional
    public void saveReply(ReplySaveRequestDto replySaveRequestDto) {
        User user = userRepository.findByUsername(replySaveRequestDto.getUserId()).orElseThrow(() -> {
            return new UsernameNotFoundException("댓글 쓰기 실패: 유저 아이디를 찾을 수 없습니다.");
        });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> {
            return new IllegalIdentifierException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다.");
        });
        System.out.println(replySaveRequestDto.toString());
        NestedReply reply = NestedReply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .parentId(replySaveRequestDto.getParentId())
                .depth(replySaveRequestDto.getDepth())
                .rootId(replySaveRequestDto.getRootId())
                .build();
        // replyRepository.sqlSave(user.getId(), board.getId(), replySaveRequestDto.getContent(), );
        replyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(int id) {
        replyRepository.deleteById(id);
    }

    @Transactional
    public void updateReply(int id, String content) {
        NestedReply reply = replyRepository.getOne(id);
        reply.setContent(content);
        replyRepository.save(reply);
    }

    public List<ReplyDto> getTimeAgoReplies(List<NestedReply> replies) {
        List<ReplyDto> timeAgoReplies = new ArrayList<>();
        ReplyDto dto;
        if(replies == null) {
            System.out.println("Nested reply is null");
            return null;
        }
        for (NestedReply reply : replies) {

            long lastUpdated = reply.getUpdateDate()!=null?reply.getUpdateDate().getTime():reply.getCreateDate().getTime();
            String timeAgo = timeAgoUtil.toRelative(lastUpdated);
            dto = new ReplyDto(reply, timeAgo);

            dto.setTimeAgo(timeAgo);
            timeAgoReplies.add(dto);
        }
        return timeAgoReplies;
    }

}
