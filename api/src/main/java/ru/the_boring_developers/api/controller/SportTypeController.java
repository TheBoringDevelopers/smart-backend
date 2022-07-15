package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.the_boring_developers.api.service.sport_type.SportTypeService;
import ru.the_boring_developers.auth.annotation.UserId;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/sport_type")
@AllArgsConstructor
public class SportTypeController {

    private final SportTypeService sportTypeService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<GetSportTypeDto>> findAll(@UserId Long userId) {
        return ok(sportTypeService.findAll());
    }
}
