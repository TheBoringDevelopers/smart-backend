package ru.the_boring_developers.common.repository.user_image_link;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.link.UserImageLink;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class UserImageLinkRepositoryImpl implements UserImageLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(UserImageLink userImageLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user_image_link (user_id, image_id) VALUES (?, ?)");
            int idx = 0;
            ps.setLong(++idx, userImageLink.getUserId());
            ps.setLong(++idx, userImageLink.getImageId());
            return ps;
        });
    }

    @Override
    public void delete(Long userId) {
        jdbcOperations.update("DELETE FROM user_image_link WHERE user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                });
    }
}
