package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;
    private String content;
    private String writerEmail;
    private String writerName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCnt;

    public Board dtoToEntity() {
        Member member = Member.builder()
                .email(this.writerEmail)
                .build();
        return Board.builder()
                .bno(this.bno)
                .title(this.title)
                .content(this.content)
                .writer(member)
                .build();
    }
}
