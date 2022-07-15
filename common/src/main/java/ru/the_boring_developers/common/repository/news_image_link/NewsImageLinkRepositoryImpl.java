package ru.the_boring_developers.common.repository.news_image_link;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.news.link.NewsImageLink;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class NewsImageLinkRepositoryImpl implements NewsImageLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(NewsImageLink newsImageLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO news_image_link (news_id, image_id) VALUES (?, ?)"
            );
            int idx = 0;
            ps.setLong(++idx, newsImageLink.getNewsId());
            ps.setLong(++idx, newsImageLink.getImageId());
            return ps;
        });
    }

    @Override
    public Long find(Long newsId) {
        return jdbcOperations.query(
                "SELECT image_id FROM news_image_link WHERE news_id = ?",
                ps -> {
                    int idx = 0;
                    ps.setLong(++idx, newsId);
                },
                rs -> rs.next() ? rs.getLong(0) : null);
    }
}
