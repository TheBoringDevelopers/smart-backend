package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.the_boring_developers.api.service.image.ImageService;
import ru.the_boring_developers.auth.annotation.UserId;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> upload(@UserId Long userId,
                                       @RequestParam MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            Long imageId = imageService.create(userId, IOUtils.toByteArray(is));
            return ok(imageId);
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Map<Long, byte[]>> getById(@UserId Long userId,
                                                     @RequestParam("ids") List<Long> ids) {
        return ok(imageService.find(userId, ids));
    }
}
