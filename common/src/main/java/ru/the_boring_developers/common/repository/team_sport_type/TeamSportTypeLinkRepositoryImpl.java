package ru.the_boring_developers.common.repository.team_sport_type;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.team.link.TeamSportTypeLink;

import java.sql.PreparedStatement;

@Repository
@AllArgsConstructor
public class TeamSportTypeLinkRepositoryImpl implements TeamSportTypeLinkRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    public void create(TeamSportTypeLink teamSportTypeLink) {
        jdbcOperations.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO team_sport_type_link (team_id, sport_type_id) VALUES (?, ?)");
            int idx = 0;
            ps.setLong(++idx, teamSportTypeLink.getTeamId());
            ps.setLong(++idx, teamSportTypeLink.getSportTypeId());
            return ps;
        });
    }
}
