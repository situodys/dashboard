package com.example.board.repository;

import com.example.board.entity.Member;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() throws Exception{
        //give
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .name("user" + i)
                    .password("1111")
                    .build();
            memberRepository.save(member);
        });

        //when

        //then
        assertEquals(100,memberRepository.count());
    }
}