package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListInfoDTO {
    private String title;
    private String writer;
    private Long replyCount;

}
