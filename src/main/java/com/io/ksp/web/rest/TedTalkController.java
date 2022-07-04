package com.io.ksp.web.rest;

import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.service.TedTalkService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ksp")
public class TedTalkController {
    private TedTalkService tedTalkService;

    public TedTalkController(TedTalkService tedTalkService) {
        this.tedTalkService = tedTalkService;
    }

    @GetMapping("v1/ted-talks")
    public ResponseEntity<Page<TedTalkEntity>> findTedTalk(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "views", required = false) Long views,
            @RequestParam(value = "likes", required = false) Long likes,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return new ResponseEntity<>(tedTalkService.findTedTalk(title, author, views, likes, page, size), HttpStatus.OK);
    }
}
