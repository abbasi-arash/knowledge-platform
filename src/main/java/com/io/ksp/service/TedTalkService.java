package com.io.ksp.service;

import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.dto.TedTalkRequest;
import com.io.ksp.repository.TedTalkRepository;
import com.io.ksp.repository.TedTalkSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    public TedTalkEntity registerTedTalk(TedTalkRequest tedTalkRequest) {
        TedTalkEntity tedTalkEntity = TedTalkEntity.builder()
                .author(tedTalkRequest.getAuthor())
                .date(tedTalkRequest.getDate())
                .title(tedTalkRequest.getTitle())
                .likes(tedTalkRequest.getLikes())
                .views(tedTalkRequest.getViews())
                .link(tedTalkRequest.getLink())
                .build();
        return tedTalkRepository.save(tedTalkEntity);
    }

    public TedTalkEntity modifyTedTalk(Long id, TedTalkRequest tedTalkRequest) {
        Optional<TedTalkEntity> tedTalkEntity = tedTalkRepository.findById(id);
        if (!tedTalkEntity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ted Talk with this id not exist");
        }
        tedTalkEntity.get().setTitle(tedTalkRequest.getTitle());
        tedTalkEntity.get().setAuthor(tedTalkRequest.getAuthor());
        tedTalkEntity.get().setDate(tedTalkRequest.getDate());
        tedTalkEntity.get().setLikes(tedTalkRequest.getLikes());
        tedTalkEntity.get().setViews(tedTalkRequest.getViews());
        tedTalkEntity.get().setLink(tedTalkRequest.getLink());

        return tedTalkRepository.save(tedTalkEntity.get());
    }

    public void removeTedTalk(Long id) {
        Optional<TedTalkEntity> tedTalkEntity = tedTalkRepository.findById(id);
        if (!tedTalkEntity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ted Talk with this id not exist");
        }
        tedTalkRepository.delete(tedTalkEntity.get());
    }
}
