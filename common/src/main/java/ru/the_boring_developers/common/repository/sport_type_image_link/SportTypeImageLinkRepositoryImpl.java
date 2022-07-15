package ru.the_boring_developers.common.repository.sport_type_image_link;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.sport_type.link.SportTypeImageLink;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class SportTypeImageLinkRepositoryImpl implements SportTypeImageLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(SportTypeImageLink sportTypeImageLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO sport_type_image_link (sport_type_id, image_id) VALUES (?, ?)");
            int idx = 0;
            ps.setLong(++idx, sportTypeImageLink.getSportTypeId());
            ps.setLong(++idx, sportTypeImageLink.getImageId());
            return ps;
        });
    }
}
