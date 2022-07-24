package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() throws Exception{
        //give
        BoardDTO dto = BoardDTO.builder()
                .title("test")
                .content("test ...")
                .writerEmail("user11@aaa.com")
                .build();
        //when
        Long registerId = boardService.register(dto);
        //then
        assertEquals(101,registerId);
    }

    @Test
    public void testGetList() throws Exception{
        //give
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        //when

        PageResponseDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        //then

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() throws Exception{
        //give
        Long boardId=99L;
        //when
        BoardDTO boardDTO = boardService.get(boardId);
        //then
        System.out.println(boardDTO);
    }

    @Test
    public void testRemove() throws Exception{
        //give
        Long boardId=99L;
        //when
        boardService.removeWithReplies(boardId);
        //then
        assertEquals(null,boardService.get(boardId));
    }
}