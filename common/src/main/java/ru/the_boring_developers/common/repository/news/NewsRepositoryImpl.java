package ru.the_boring_developers.common.repository.news;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.news.News;
import ru.the_boring_developers.common.utils.DateUtil;
import ru.the_boring_developers.common.utils.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public List<News> findAll() {
        return jdbcOperations.query(
                "SELECT * FROM news",
                (rs, rowNum) -> extractValue(rs));
    }

    @Override
    public Long create(News news) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        OffsetDateTime currentTime = DateUtil.now();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO news (title, description, created) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setString(++idx, news.getTitle());
            ps.setString(++idx, news.getDescription());
            ps.setObject(++idx, currentTime);
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    public News extractValue(ResultSet rs) throws SQLException {
        News news = new News();
        news.setId(rs.getLong("id"));
        news.setTitle(rs.getString("title"));
        news.setDescription(rs.getString("description"));
        news.setCreated(DbUtils.getOffsetDateTime(rs, "created"));
        return news;
    }
}
