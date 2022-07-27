package com.example.board.repository.search;


import com.example.board.entity.Board;
import static com.example.board.entity.QBoard.board;
import static com.example.board.entity.QMember.member;
import com.example.board.entity.QBoard;

import static com.example.board.entity.QReply.reply;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class SearchBoardRepositoryImpl implements SearchBoardRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Board searchOneBoard(Long bno) {
        log.info("searchOneBoard..........");

        List<Tuple> result = queryFactory.select(board, member.email, reply.count())
                .from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .groupBy(board)
                .fetch();

        log.info(result);
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        List<OrderSpecifier> orders = getOrderSpecifier(pageable.getSort());

        List<Tuple> result = queryFactory.select(board, member, reply.count())
                .from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(
                        board.bno.gt(0L),
                        getEqTitle(type, keyword),
                        getEqContent(type, keyword),
                        getEqWriter(type, keyword)
                )
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(board)
                .fetch();


        int size = queryFactory.select(board, member, reply.count())
                .from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(
                        board.bno.gt(0L),
                        getEqTitle(type, keyword),
                        getEqContent(type, keyword),
                        getEqWriter(type, keyword)
                )
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .groupBy(board)
                .fetch().size();

        return new PageImpl<Object[]>(
                result.stream()
                        .map(tuple -> tuple.toArray())
                        .collect(Collectors.toList()), pageable, size
        );
    }

    private BooleanExpression getEqTitle(String type, String keyword) {
        if (type == null || !type.contains("t")) {
            return null;
        }
        return board.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression getEqContent(String type, String keyword) {
        if (type == null || !type.contains("c")) {
            return null;
        }
        return board.content.containsIgnoreCase(keyword);
    }

    private BooleanExpression getEqWriter(String type, String keyword) {
        if (type == null || !type.contains("w")) {
            return null;
        }
        return board.writer.name.containsIgnoreCase(keyword);
    }


    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderExpression = new PathBuilder(Board.class, "board");
            orders.add(new OrderSpecifier(direction, orderExpression.get(prop)));
        });
        return orders;
    }
}
