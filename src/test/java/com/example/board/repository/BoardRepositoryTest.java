package com.example.board.repository;

import com.example.board.dto.BoardListInfoDTO;
import com.example.board.dto.BoardSearch;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

import static org.assertj.core.api.Assertions.*;


import com.example.board.repository.search.SearchRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;



@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private SearchRepository searchRepository;

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
        //assertEquals(100, boardRepository.count());
    }

    @Test
    @Transactional
    public void readOneRecord() throws Exception{
        //give
        Long bno = 50L;
        //when
        Optional<Board> result = boardRepository.findById(bno);
        Board board=new Board();
        //then
        if (result.isPresent()) {
            board = result.get();
        }

        System.out.println(board);

        assertThat(board.getBno()).isEqualTo(50);
        assertThat(board.getWriter().getEmail()).isEqualTo("user50@aaa.com");
    }

    @Test
    public void getBoardWithWriter() throws Exception{
        //give
        Object result = boardRepository.getBoardWithWriter(50L);
        //when
        Object[] arr = (Object[]) result;

        //then
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void getBoardWithReply() throws Exception{
        //give
        List<Object[]> boardWithReply = boardRepository.getBoardWithReply(50L);
        //when

        //then
        for (Object[] arr : boardWithReply) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void getBoardWithReplyCount() throws Exception{
        //give
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        //when
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        //then
        result.get().forEach(row->{
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void getBoardByBno() throws Exception{
        //give
        Object result = boardRepository.getBoardByBno(99L);
        //when
        Object[] arr = (Object[])result;
        //then
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearchOneBoard() throws Exception{
        //give

        //when

        //then
        System.out.println(boardRepository.searchOneBoard(1L));
    }

    @Test
    public void testSearchPage() throws Exception{
        //give
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        //when
        Page<Object[]> t = boardRepository.searchPage("t", "1", pageable);
        //then
        for (Object[] objects : t) {

        }
    }
    @Test
    public void newSearch() throws Exception{
        //give
        BoardSearch search = BoardSearch.builder()
                .page(1)
                .title("1")
                .build();
        //when

        PageImpl<BoardListInfoDTO> boardList = searchRepository.getBoardList(search);
        //then
        for (BoardListInfoDTO boardListInfoDTO : boardList.getContent()) {
            System.out.println(boardListInfoDTO);
        }
        System.out.println("boardList fetch count = " + boardList.getTotalElements());
        System.out.println("boardList total pages= " + boardList.getTotalPages());
    }
}