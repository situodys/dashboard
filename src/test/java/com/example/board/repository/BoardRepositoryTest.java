package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoards() throws Exception{
        //give
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });

        //when

        //then
        assertEquals(100, boardRepository.count());
    }
}