package com.example.board.repository;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import static org.junit.jupiter.api.Assertions.*;

import com.example.board.entity.Reply;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;



@SpringBootTest
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReplies() throws Exception{
        //give
        IntStream.rangeClosed(1,300).forEach(i->{
            long bno = (long)(Math.random()*100) +1;

            Board board= Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply ....." + i)
                    .replyer("guest")
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });

        //when

        //then
        assertEquals(300, replyRepository.count());
    }

    @Test
    public void testGetRepliesByBoardOrderByRno() throws Exception{
        //give
        Board board = Board.builder()
                .bno(20L)
                .build();
        //when
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);
        //then
        for (Reply reply : replies) {
            System.out.println(reply);
        }
    }
    
    @Test
    public void testconvertDTO2Entity() throws Exception{
        //give
        Optional<Reply> result = replyRepository.findById(300L);
        Reply reply=null;
        ReplyDTO replyDTO=null;
        //when
        if (result.isPresent()) {
            reply = result.get();
        }
        replyDTO = reply.toDTO();
        reply = replyDTO.toEntity();
        System.out.println(reply.getBoard().getBno());
        //then
    }
}