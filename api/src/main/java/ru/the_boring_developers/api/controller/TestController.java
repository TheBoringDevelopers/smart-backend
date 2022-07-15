package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.the_boring_developers.api.service.news.NewsService;
import ru.the_boring_developers.api.service.sport_type.SportTypeService;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.news.dto.CreateNewsDto;
import ru.the_boring_developers.common.entity.sport_type.dto.CreateSportTypeDto;
import ru.the_boring_developers.common.repository.image.ImageRepository;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final SportTypeService sportTypeService;
    private final ImageRepository imageRepository;
    private final NewsService newsService;

    @PostMapping(path = "/sport_type")
    public ResponseEntity findAll(@RequestBody CreateSportTypeDto createSportTypeDto) {
        sportTypeService.create(createSportTypeDto);
        return ok().build();
    }

    @PostMapping(path = "/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> upload(@RequestParam MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            Long imageId = imageRepository.create(Image.builder().content(IOUtils.toByteArray(is)).build());
            return ok(imageId);
        }
    }

    @PostMapping(path = "/news")
    public ResponseEntity findAll(@RequestBody CreateNewsDto createNewsDto) {
        newsService.create(createNewsDto);
        return ok().build();
    }
}
