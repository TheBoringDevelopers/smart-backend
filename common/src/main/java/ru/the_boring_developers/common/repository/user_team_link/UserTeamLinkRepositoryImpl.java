package ru.the_boring_developers.common.repository.user_team_link;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.link.UserTeamLink;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTeamLinkRepositoryImpl implements UserTeamLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(UserTeamLink userTeamLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user_team_link (user_id, team_id, leader) VALUES (?, ?, ?)");
            int idx = 0;
            ps.setLong(++idx, userTeamLink.getUserId());
            ps.setLong(++idx, userTeamLink.getTeamId());
            ps.setBoolean(++idx, userTeamLink.isLeader());
            return ps;
        });
    }

    @Override
    public void delete(Long userId, Long teamId) {
        jdbcOperations.update("DELETE FROM user_image_link WHERE user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                });
    }

    @Override
    public UserTeamLink find(Long userId, Long teamId) {
        return jdbcOperations.query(
                "SELECT * FROM user_team_link WHERE user_id = ? AND team_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                    ps.setLong(++idx, teamId);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    @Override
    public List<UserTeamLink> find(Long teamId) {
        return jdbcOperations.query(
               "SELECT * FROM user_team_link WHERE team_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, teamId);
                }, (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public void setLeader(Long teamId, Long userId) {
        jdbcOperations.update("UPDATE user_team_link SET leader = ? WHERE team_id = ? AND user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setBoolean(++idx, true);
                    ps.setLong(++idx, teamId);
                    ps.setLong(++idx, userId);
                });
    }

    public UserTeamLink extractValue(ResultSet rs) throws SQLException {
        UserTeamLink userTeamLink = new UserTeamLink();
        userTeamLink.setTeamId(rs.getLong("team_id"));
        userTeamLink.setUserId(rs.getLong("user_id"));
        userTeamLink.setLeader(rs.getBoolean("leader"));
        return userTeamLink;
    }
}
