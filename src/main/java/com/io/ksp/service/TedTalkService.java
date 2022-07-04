package com.io.ksp.service;

import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.repository.TedTalkRepository;
import com.io.ksp.repository.TedTalkSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TedTalkService {
    private final TedTalkRepository tedTalkRepository;

    public TedTalkService(TedTalkRepository tedTalkRepository) {
        this.tedTalkRepository = tedTalkRepository;
    }

    public Page<TedTalkEntity> findTedTalk(String title, String author, Long views, Long likes, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "title"));

        TedTalkSpecification spec = new TedTalkSpecification(title, author, views, likes);
        return tedTalkRepository.findAll(spec, pageRequest);
    }
}
