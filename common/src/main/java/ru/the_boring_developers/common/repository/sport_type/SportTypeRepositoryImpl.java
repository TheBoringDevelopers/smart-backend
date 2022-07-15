package ru.the_boring_developers.common.repository.sport_type;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.sport_type.SportType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class SportTypeRepositoryImpl implements SportTypeRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public Long create(SportType sportType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO sport_type (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setString(++idx, sportType.getName());
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public List<SportType> findByTeamId(Long teamId) {
        return jdbcOperations.query(
                "SELECT st.* FROM sport_type st JOIN team_sport_type_link tstl ON st.id = tstl.sport_type_id WHERE tstl.team_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, teamId);
                }, (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public SportType findByName(String name) {
        return jdbcOperations.query(
                "SELECT * FROM sport_type WHERE name = ?",
                ps -> {
                    int idx = 0;
                    ps.setString(++idx, name);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    @Override
    public List<SportType> findAll() {
        return jdbcOperations.query(
                "SELECT * FROM sport_type",
                (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public List<SportType> findByUserId(Long userId) {
        return jdbcOperations.query(
                "SELECT st.* FROM sport_type st JOIN user_sport_type_link ustl ON st.id = ustl.sport_type_id WHERE ustl.user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                }, (rs, rowNum) -> extractValue(rs));
    }

    public SportType extractValue(ResultSet rs) throws SQLException {
        SportType sportType = new SportType();
        sportType.setId(rs.getLong("id"));
        sportType.setName(rs.getString("name"));
        return sportType;
    }
}
