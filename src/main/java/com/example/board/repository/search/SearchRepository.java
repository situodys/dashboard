package com.example.board.repository.search;

import com.example.board.dto.BoardListInfoDTO;
import com.example.board.dto.BoardSearch;
import org.springframework.data.domain.PageImpl;

public interface SearchRepository {

    PageImpl<BoardListInfoDTO> getBoardList(BoardSearch boardSearch);
}
