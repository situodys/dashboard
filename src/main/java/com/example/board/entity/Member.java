package com.example.board.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity{

    @Id
    private String email;

    private String name;

    private String password;

}
