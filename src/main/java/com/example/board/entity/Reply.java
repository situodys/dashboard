package com.example.board.entity;

import com.example.board.dto.ReplyDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public ReplyDTO toDTO(){
        return ReplyDTO.builder()
                .rno(this.rno)
                .text(this.text)
                .replyer(this.replyer)
                .regDate(this.getRegDate())
                .modDate(this.getModDate())
                .bno(this.board.getBno())
                .build();
    }
}
