package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    
    private Long rno;
    
    private String text;
    
    private String replyer;

    private Long bno;

    private LocalDateTime regDate,modDate;

    public Reply toEntity(){
        return Reply.builder()
                .rno(this.rno)
                .text(this.text)
                .replyer(this.replyer)
                .board(Board.builder().bno(this.bno).build())
                .build();
    }
}
