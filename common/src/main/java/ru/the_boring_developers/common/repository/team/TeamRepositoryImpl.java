package ru.the_boring_developers.common.repository.team;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.team.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public Team findByUserId(Long userId) {
        return jdbcOperations.query(
                "SELECT team.* FROM team JOIN user_team_link utl ON team.id = utl.team_id WHERE utl.user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    @Override
    public List<Team> find(List<Long> sportTypeIds, Boolean empty) {
        return jdbcOperations.query(
                MessageFormat.format(
                        "SELECT DISTINCT team.* FROM team {0} {1}",
                        empty != null && empty ? "WHERE" : "",
                        empty != null && empty ? "team.id IN (SELECT team_id as id FROM user_team_link GROUP BY team_id HAVING COUNT(*) < 6)" : ""),
                ps -> {
                int idx = 0;
                }, (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public void delete(Long userId, Long teamId) {
        jdbcOperations.update("DELETE FROM user_team_link WHERE user_id = ? AND team_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                    ps.setLong(++idx, teamId);
                });
    }

    @Override
    public void delete(Long teamId) {
        jdbcOperations.update("DELETE FROM team WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, teamId);
                });
    }

    @Override
    public Long create(Team team) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO team (name, city, organization, organization_name, description) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setString(++idx, team.getName());
            ps.setString(++idx, team.getCity());
            ps.setBoolean(++idx, team.isOrganization());
            ps.setString(++idx, team.getOrganizationName());
            ps.setString(++idx, team.getDescription());
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    public Team extractValue(ResultSet rs) throws SQLException {
        Team team = new Team();
        team.setId(rs.getLong("id"));
        team.setName(rs.getString("name"));
        team.setCity(rs.getString("city"));
        team.setDescription(rs.getString("description"));
        team.setOrganization(rs.getBoolean("organization"));
        team.setOrganizationName(rs.getString("organization_name"));
        return team;
    }
}
