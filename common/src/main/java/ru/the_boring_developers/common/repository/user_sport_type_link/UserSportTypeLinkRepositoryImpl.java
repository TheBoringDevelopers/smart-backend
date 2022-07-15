package ru.the_boring_developers.common.repository.user_sport_type_link;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.link.UserSportTypeLink;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class UserSportTypeLinkRepositoryImpl implements UserSportTypeLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(UserSportTypeLink userSportTypeLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user_sport_type_link (user_id, sport_type_id) VALUES (?, ?)");
            int idx = 0;
            ps.setLong(++idx, userSportTypeLink.getUserId());
            ps.setLong(++idx, userSportTypeLink.getSportTypeId());
            return ps;
        });
    }

    @Override
    public void delete(Long userId) {
        jdbcOperations.update("DELETE FROM user_sport_type_link WHERE user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                });
    }
}
