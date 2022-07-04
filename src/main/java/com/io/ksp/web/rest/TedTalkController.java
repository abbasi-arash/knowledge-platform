package com.io.ksp.web.rest;

import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.dto.TedTalkRequest;
import com.io.ksp.service.TedTalkService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("v1/ted-talks")
    public ResponseEntity<TedTalkEntity> registerTedTalk(@Valid @RequestBody TedTalkRequest tedTalkRequest) {
        return new ResponseEntity<>(tedTalkService.registerTedTalk(tedTalkRequest), HttpStatus.CREATED);
    }

    @PutMapping("v1/ted-talks/{id}")
    public ResponseEntity<TedTalkEntity> modifyTedTalk(
            @Valid @RequestBody TedTalkRequest tedTalkRequest,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(tedTalkService.modifyTedTalk(id, tedTalkRequest), HttpStatus.OK);
    }

    @DeleteMapping("v1/ted-talks/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeTedTalk(
            @PathVariable Long id
    ) {
        tedTalkService.removeTedTalk(id);
    }

}
