package ru.the_boring_developers.common.repository.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.registration.Confirmation;
import ru.the_boring_developers.common.utils.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;

@Repository
@RequiredArgsConstructor
public class ConfirmationRepositoryImpl implements ConfirmationRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public long create(Confirmation confirmation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        OffsetDateTime currentTime = OffsetDateTime.now();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement("INSERT INTO confirmation " +
                    "(login, session_id, code, attempt_count, created, changed) " +
                    "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setString(++idx, confirmation.getLogin());
            ps.setString(++idx, confirmation.getSessionId());
            ps.setString(++idx, confirmation.getCode());
            ps.setInt(++idx, confirmation.getAttemptCount());
            ps.setObject(++idx, currentTime);
            ps.setObject(++idx, currentTime);
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    @Override
    public Confirmation getLast(String login) {
        return jdbcOperations.query("SELECT * FROM confirmation WHERE login = ? ORDER BY created DESC",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, login);
                }, rs -> rs.next() ? extractUserConfirmationInfo(rs) : null
        );
    }

    @Override
    public void incrementAttemptCount(long confirmationId) {
        jdbcOperations.update("UPDATE confirmation SET attempt_count = attempt_count + 1, changed = ? WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setObject(++idx, OffsetDateTime.now());
                    ps.setLong(++idx, confirmationId);
                }
        );
    }

    @Override
    public boolean deleteAll(String login) {
        return jdbcOperations.update("DELETE FROM confirmation WHERE login = ?",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, login);
                }) > 0;
    }

    private Confirmation extractUserConfirmationInfo(ResultSet rs) throws SQLException {
        Confirmation confirmation = new Confirmation();
        confirmation.setId(rs.getLong("id"));
        confirmation.setLogin(rs.getString("login"));
        confirmation.setSessionId(rs.getString("session_id"));
        confirmation.setCode(rs.getString("code"));
        confirmation.setAttemptCount(rs.getInt("attempt_count"));
        confirmation.setCreated(DbUtils.getOffsetDateTime(rs, "created"));
        confirmation.setChanged(DbUtils.getOffsetDateTime(rs, "changed"));
        return confirmation;
    }
}
