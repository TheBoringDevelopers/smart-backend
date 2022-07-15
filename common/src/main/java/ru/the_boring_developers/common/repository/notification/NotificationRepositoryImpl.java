package ru.the_boring_developers.common.repository.notification;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;
import ru.the_boring_developers.common.utils.DateUtil;
import ru.the_boring_developers.common.utils.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(Notification notification) {
        OffsetDateTime now = DateUtil.now();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO notification (name, description, type, created)" +
                            " VALUES (?, ?, ?, ?)");
            int idx = 0;
            ps.setString(++idx, notification.getName());
            ps.setString(++idx, notification.getDescription());
            ps.setString(++idx, notification.getType().name());
            ps.setObject(++idx, now);
            return ps;
        });
    }

    @Override
    public List<Notification> find(Long userId, List<NotificationType> notificationTypes) {
        return jdbcOperations.query(
                "SELECT * FROM notification WHERE user_id = ? AND type IN (" + DbUtils.prepareQuestionStatement(notificationTypes.size()) + ")",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                    for (NotificationType notificationType : notificationTypes) {
                        ps.setString(++idx, notificationType.name());
                    }
                }, (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public void update(Long userId, Long notificationId) {
        OffsetDateTime now = DateUtil.now();
        jdbcOperations.update("UPDATE notification SET user_id = ? AND viewed = ? WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                    ps.setObject(++idx, now);
                    ps.setLong(++idx, notificationId);
                });
    }

    public Notification extractValue(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getLong("id"));
        notification.setName(rs.getString("name"));
        notification.setDescription(rs.getString("description"));
        notification.setType(NotificationType.valueOf(rs.getString("type")));
        notification.setCreated(DbUtils.getOffsetDateTime(rs, "created"));
        notification.setViewed(DbUtils.getOffsetDateTime(rs, "viewed"));
        return notification;
    }
}
