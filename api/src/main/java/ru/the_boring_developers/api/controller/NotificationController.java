package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.the_boring_developers.api.service.notification.NotificationService;
import ru.the_boring_developers.auth.annotation.UserId;
import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/secured/notification")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<Notification>> findAll(@UserId Long userId,
                                                      @RequestParam List<NotificationType> types) {
        return ok(notificationService.find(userId, types));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@UserId Long userId,
                                 @PathVariable(name = "id") Long notificationId) {
        notificationService.update(userId, notificationId);
        return ok().build();
    }
}
