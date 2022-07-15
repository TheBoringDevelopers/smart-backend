package ru.the_boring_developers.common.repository.team;


import ru.the_boring_developers.common.entity.team.Team;
import ru.the_boring_developers.common.repository.Repository;

import java.util.List;

public interface TeamRepository extends Repository {
    Team findByUserId(Long userId);
    List<Team> find(List<Long> sportTypes, Boolean empty);
    void delete(Long userId, Long teamId);
    void delete(Long teamId);
    Long create(Team team);
}
