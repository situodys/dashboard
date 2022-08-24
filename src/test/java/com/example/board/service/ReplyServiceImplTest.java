package com.example.board.service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testGetList() throws Exception{
        //give
        long bno=50;
        //when
        List<ReplyDTO> dtos = replyService.getList(48L);

        //then
        for (ReplyDTO dto : dtos) {
            System.out.println(dto);
        }
    }

    @Test
    public void testRegister() throws Exception{
        //give
        ReplyDTO dto = ReplyDTO.builder()
                .replyer("test1")
                .text("hi test")
                .bno(48L)
                .build();
        //when
        Long newReply = replyService.register(dto);
        //then
        System.out.println(newReply);
    }

    @Test
    public void testModify() throws Exception{
        //give
        ReplyDTO dto = ReplyDTO.builder()
                .rno(303L)
                .replyer("test1")
                .text("hi test modify")
                .bno(48L)
                .build();
        //when
        replyService.modify(dto);
        //then
        System.out.println(replyRepository.findById(303L));
    }

    @Test
    public void testRemove() throws Exception{
        //give

        //when
        replyService.remove(303L);
        //then
        Optional<Reply> result = replyRepository.findById(303L);
        if(!result.isPresent())
            System.out.println("success");
    }
}