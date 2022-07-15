package ru.the_boring_developers.common.repository.notification;


import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;
import ru.the_boring_developers.common.repository.Repository;

import java.util.List;

public interface NotificationRepository extends Repository {
    void create(Notification notification);

    List<Notification> find(Long userId, List<NotificationType> notificationTypes);

    void update(Long userId, Long notificationId);
}
