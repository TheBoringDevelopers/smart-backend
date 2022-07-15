package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.the_boring_developers.api.service.user.UserService;
import ru.the_boring_developers.auth.annotation.UserId;
import ru.the_boring_developers.common.entity.user.dto.GetUserDto;
import ru.the_boring_developers.common.entity.user.dto.UpdateUserDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserDto> findUser(@UserId Long userId) {
        GetUserDto user = userService.findDto(userId);
        if (user != null) {
            return ok(user);
        }
        return badRequest().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetUserDto>> findListWithoutTeam(@UserId Long userId) {
        return ok(userService.findDtos(userId));
    }

    @PutMapping
    public ResponseEntity edit(@UserId Long userId, @RequestBody UpdateUserDto user) {
        user.getUser().setId(userId);
        userService.update(user);
        return ok().build();
    }

    @PutMapping(params = {"old_password", "new_password"})
    public ResponseEntity editProfilePassword(@UserId Long userId,
                                              @RequestParam("old_password") String oldPassword,
                                              @RequestParam("new_password") String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return ok().build();
    }
}
