package com.io.ksp.repository;

import com.io.ksp.domain.TedTalkEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TedTalkSpecification implements Specification<TedTalkEntity> {
    private final String title;
    private final String author;
    private final Long views;
    private final Long likes;

    public TedTalkSpecification(String title, String author, Long views, Long likes) {
        this.title = title;
        this.author = author;
        this.views = views;
        this.likes = likes;
    }

    @Override
    public Predicate toPredicate(Root<TedTalkEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(title)) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
        if (Objects.nonNull(author)) {
            predicates.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
        }
        if (Objects.nonNull(views)) {
            predicates.add(criteriaBuilder.equal(root.get("views"), views));
        }
        if (Objects.nonNull(likes)) {
            predicates.add(criteriaBuilder.equal(root.get("likes"), likes));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
