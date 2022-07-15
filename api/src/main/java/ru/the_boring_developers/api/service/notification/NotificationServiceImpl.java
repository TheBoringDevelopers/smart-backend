package ru.the_boring_developers.api.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;
import ru.the_boring_developers.common.repository.notification.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> find(Long userId, List<NotificationType> notificationTypes) {
        return notificationRepository.find(userId, notificationTypes);
    }

    @Override
    public void update(Long userId, Long notificationId) {
        notificationRepository.update(userId, notificationId);
    }
}
