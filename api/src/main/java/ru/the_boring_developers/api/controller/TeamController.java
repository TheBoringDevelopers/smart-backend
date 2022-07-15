package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.the_boring_developers.api.service.team.TeamService;
import ru.the_boring_developers.auth.annotation.UserId;
import ru.the_boring_developers.common.entity.team.dto.CreateTeamDto;
import ru.the_boring_developers.common.entity.team.dto.GetTeamDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Long> create(@UserId Long userId,
                                       @RequestBody CreateTeamDto createTeamDto) {
        return ok(teamService.create(userId, createTeamDto));
    }

    @PostMapping(path = "/invite")
    public ResponseEntity invite(@UserId Long userId,
                                 @RequestParam Long teamId,
                                 @RequestParam Long requestedUserId) {
        teamService.invite(userId, teamId, requestedUserId);
        return ok().build();
    }

    @DeleteMapping(path = "/user")
    public ResponseEntity<Long> delete(@UserId Long userId,
                                       @RequestParam Long requestedUserId,
                                       @RequestParam Long teamId) {
        teamService.delete(userId, requestedUserId, teamId);
        return ok().build();
    }

    @DeleteMapping(path = "/leave")
    public ResponseEntity<Long> leave(@UserId Long userId,
                                      @RequestParam Long teamId) {
        teamService.leave(userId, teamId);
        return ok().build();
    }

    @GetMapping
    public ResponseEntity<GetTeamDto> getByUserId(@UserId Long userId) {
        return ok(teamService.find(userId));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<GetTeamDto>> find(@UserId Long userId,
                                                 @RequestParam(required = false) List<Long> sportTypes,
                                                 @RequestParam(required = false) Boolean empty) {
        return ok(teamService.find(userId, sportTypes, empty));
    }

    @DeleteMapping
    public ResponseEntity delete(@UserId Long userId,
                                 @RequestParam Long teamId) {
        teamService.delete(userId, teamId);
        return ok().build();
    }
}
