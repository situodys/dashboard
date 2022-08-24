package com.example.board.service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = replyDTO.toEntity();
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return replies.stream()
                .map(reply -> reply.toDTO())
                .collect(Collectors.toList());

    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        replyRepository.save(replyDTO.toEntity());
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
