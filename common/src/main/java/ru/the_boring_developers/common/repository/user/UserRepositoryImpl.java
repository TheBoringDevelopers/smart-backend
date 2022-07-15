package ru.the_boring_developers.common.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.utils.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcOperations jdbcOperations;

    private final UserResultSetExtractor userResultSetExtractor;

    @Override
    public User findByPhone(String phone) {
        return jdbcOperations.query(
                "SELECT * FROM user_info WHERE phone_number = ?",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, phone);
                },
                rs -> rs.next() ? userResultSetExtractor.extractValue(rs) : null);
    }

    @Override
    public User findById(Long id) {
        return jdbcOperations.query(
                "SELECT * FROM user_info WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, id);
                },
                rs -> rs.next() ? userResultSetExtractor.extractValue(rs) : null);
    }

    @Override
    public List<User> findByTeamId(Long teamId) {
        return jdbcOperations.query(
                "SELECT * FROM user_info ui JOIN user_team_link utl ON utl.user_id = ui.id WHERE utl.team_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, teamId);
                }, (rs, rowNum) -> userResultSetExtractor.extractValue(rs));
    }

    @Override
    public List<User> findWithoutTeam() {
        return jdbcOperations.query(
                "SELECT * FROM user_info ui LEFT JOIN user_team_link utl ON utl.user_id = ui.id WHERE utl.team_id IS NULL",
                (rs, rowNum) -> userResultSetExtractor.extractValue(rs));
    }

    @Override
    public boolean updatePassword(Long id, String password) {
        OffsetDateTime currentTime = DateUtil.now();
        return jdbcOperations.update(
                "UPDATE user_info SET password = ?, password_changed = ?, changed = ? " +
                        "WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, password);
                    ps.setObject(++idx, currentTime);
                    ps.setObject(++idx, currentTime);
                    ps.setLong(++idx, id);
                }) > 0;
    }

    @Override
    public boolean existsByLogin(String login) {
        return jdbcOperations.query(
                "SELECT * FROM user_info WHERE phone_number = ? ",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, login);
                }, ResultSet::next);
    }

    @Override
    public Long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        OffsetDateTime currentTime = DateUtil.now();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user_info (phone_number, password, first_name, last_name, patronymic, email, password_changed, created, changed)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setString(++idx, user.getPhoneNumber());
            ps.setString(++idx, user.getPassword());
            ps.setString(++idx, user.getFirstName());
            ps.setString(++idx, user.getLastName());
            ps.setString(++idx, user.getPatronymic());
            ps.setString(++idx, user.getEmail());
            ps.setObject(++idx, currentTime);
            ps.setObject(++idx, currentTime);
            ps.setObject(++idx, currentTime);
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public void updateLoginTime(Long userId) {
        jdbcOperations.update("UPDATE user_info SET login_time = ? WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setObject(++idx, DateUtil.now());
                    ps.setLong(++idx, userId);
                });
    }

    @Override
    public boolean update(User user) {
        int result = jdbcOperations.update("UPDATE user_info SET first_name = ?, last_name = ?, patronymic = ?, email = ?, changed = ? WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, user.getFirstName());
                    ps.setString(++idx, user.getLastName());
                    ps.setString(++idx, user.getPatronymic());
                    ps.setString(++idx, user.getEmail());
                    ps.setObject(++idx, OffsetDateTime.now());
                    ps.setLong(++idx, user.getId());
                });
        return result > 0;
    }
}
