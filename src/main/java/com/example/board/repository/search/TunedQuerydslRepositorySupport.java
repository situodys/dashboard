//package com.example.board.repository.search;
//
//import com.querydsl.core.types.EntityPath;
//import com.querydsl.core.types.dsl.PathBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
//import org.springframework.data.jpa.repository.support.Querydsl;
//import org.springframework.data.querydsl.SimpleEntityPathResolver;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.Assert;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
//@Repository
//abstract public class TunedQuerydslRepositorySupport {
//    private final Class domainClass;
//    private Querydsl querydsl;
//    private EntityManager entityManager;
//    private JPAQueryFactory queryFactory;
//
//
//    //1. 도메인 class 받기 (ex)Member.class)
//    public TunedQuerydslRepositorySupport(Class<?> domainClass) {
//        Assert.notNull(domainClass, "Domain class must not be null!");
//        this.domainClass = domainClass;
//    }
//
//
//    //2. EntityManager 주입받기
//    @Autowired
//    public void setEntityManager(EntityManager entityManager) {
//        Assert.notNull(entityManager, "EntityManager must not be null!");
//        //2-1. Path를 잡아야 Sort 명령시에 오류가 안난다.
//        JpaEntityInformation entityInformation =
//                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
//        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
//        EntityPath path = resolver.createPath(entityInformation.getJavaType());
//        //2-2. entityManager 주입 받은 것으로 querydsl, queryFactory 생성
//        this.entityManager = entityManager;
//        this.querydsl = new Querydsl(entityManager, new
//                PathBuilder<>(path.getType(), path.getMetadata()));
//        this.queryFactory = new JPAQueryFactory(entityManager);
//    }
//
//    @PostConstruct
//    public void validate() {
//        Assert.notNull(entityManager, "EntityManager must not be null!");
//        Assert.notNull(querydsl, "Querydsl must not be null!");
//        Assert.notNull(queryFactory, "QueryFactory must not be null!");
//    }
//
//
//    //3. getter로 가져다 쓸 순 있지만, 이후에 그냥 queryFactory를 호출된 형태의 method를 가져다 쓸 수 있게 해준다.
//    protected JPAQueryFactory getQueryFactory() {
//        return queryFactory;
//    }
//    protected Querydsl getQuerydsl() {
//        return querydsl;
//    }
//    protected EntityManager getEntityManager() {
//        return entityManager;
//    }
//}
