package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.the_boring_developers.api.service.news.NewsService;
import ru.the_boring_developers.auth.annotation.UserId;
import ru.the_boring_developers.common.entity.news.dto.GetNewsDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/news")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<GetNewsDto>> findAll(@UserId Long userId) {
        return ok(newsService.findAll(userId));
    }
}
