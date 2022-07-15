package ru.the_boring_developers.common.repository.sport_type;


import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.repository.Repository;

import java.util.List;

public interface SportTypeRepository extends Repository {
    Long create(SportType sportType);
    List<SportType> findByTeamId(Long teamId);
    SportType findByName(String name);
    List<SportType> findAll();
    List<SportType> findByUserId(Long userId);
}
