package com.io.ksp.repository;

import com.io.ksp.domain.TedTalkEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TedTalkRepository extends PagingAndSortingRepository<TedTalkEntity, Long>, JpaSpecificationExecutor<TedTalkEntity> {
}
