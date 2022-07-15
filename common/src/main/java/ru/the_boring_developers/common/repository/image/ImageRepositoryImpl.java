package ru.the_boring_developers.common.repository.image;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.utils.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public Long create(Image image) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO image (content) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            int idx = 0;
            ps.setBytes(++idx, image.getContent());
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public Image findByUserId(Long userId) {
        return jdbcOperations.query(
                "SELECT image.* FROM image JOIN user_image_link uil ON image.id = uil.image_id WHERE uil.user_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, userId);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    @Override
    public Image find(Long id) {
        return jdbcOperations.query(
                "SELECT * FROM image WHERE id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, id);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    @Override
    public Map<Long, byte[]> find(List<Long> ids) {
        return jdbcOperations.query(
                "SELECT * FROM image WHERE id IN (" + DbUtils.prepareQuestionStatement(ids.size()) + ")",
                ps -> {
                    int idx = 0;
                    for (Long id : ids) {
                        ps.setLong(++idx, id);
                    }
                }, (rs, rowNum) -> extractValue(rs)).stream().collect(Collectors.toMap(Image::getId, Image::getContent));
    }

    @Override
    public Image findBySportTypeId(Long sportTypeId) {
        return jdbcOperations.query(
                "SELECT image.* FROM image JOIN sport_type_image_link stil ON image.id = stil.image_id WHERE stil.sport_type_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, sportTypeId);
                },
                rs -> rs.next() ? extractValue(rs) : null);
    }

    public Image extractValue(ResultSet rs) throws SQLException {
        Image image = new Image();
        image.setId(rs.getLong("id"));
        image.setContent(rs.getBytes("content"));
        return image;
    }
}
