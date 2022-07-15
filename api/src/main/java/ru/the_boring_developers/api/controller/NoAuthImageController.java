package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.the_boring_developers.api.service.image.ImageService;
import ru.the_boring_developers.common.entity.image.Image;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class NoAuthImageController {

    private final ImageService imageService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<byte[]> getById(@PathVariable("id") Long id) {
        Image image = imageService.find(id);
        if (image == null) {
            return notFound().build();
        }
        return ok(image.getContent());
    }
}
