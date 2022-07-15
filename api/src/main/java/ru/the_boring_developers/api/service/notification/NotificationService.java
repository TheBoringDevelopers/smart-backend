package ru.the_boring_developers.api.service.notification;

import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;

import java.util.List;

public interface NotificationService {

    List<Notification> find(Long userId, List<NotificationType> notificationTypes);

    void update(Long userId, Long notificationId);
}
