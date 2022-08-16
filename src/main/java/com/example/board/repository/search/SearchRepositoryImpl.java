package com.example.board.repository.search;

import com.example.board.dto.BoardListInfoDTO;
import com.example.board.dto.BoardSearch;
import com.example.board.entity.Board;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.board.entity.QBoard.board;
import static com.example.board.entity.QMember.member;
import static com.example.board.entity.QReply.reply;

@Repository
public class SearchRepositoryImpl extends QuerydslRepositorySupport implements SearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SearchRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Board.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<BoardListInfoDTO> getBoardList(BoardSearch boardSearch) {

        PageRequest pageRequest = PageRequest.of(boardSearch.getPage(), boardSearch.getSize());

        JPAQuery<BoardListInfoDTO> boardListInfoDTOJPAQuery = jpaQueryFactory.select(Projections.constructor(BoardListInfoDTO.class,
                        board.title,
                        member.name,
                        reply.count()
                ))
                .from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(
                        getEqTitle(boardSearch.getTitle()),
                        getEqContent(boardSearch.getContent()),
                        getEqWriter(boardSearch.getWriter())
                )
                .groupBy(board);

        int fetchCount = boardListInfoDTOJPAQuery.fetch().size();

        List<BoardListInfoDTO> fetch = this.getQuerydsl().applyPagination(pageRequest, boardListInfoDTOJPAQuery).fetch();
        return new PageImpl<>(fetch,pageRequest,fetchCount);
    }


    private BooleanExpression getEqTitle(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) return null;
        return board.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression getEqContent(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) return null;
        return board.content.containsIgnoreCase(keyword);
    }

    private BooleanExpression getEqWriter(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) return null;
        return board.writer.name.containsIgnoreCase(keyword);
    }
}
