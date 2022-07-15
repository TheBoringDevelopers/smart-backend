package ru.the_boring_developers.api.service.sport_type;

import ru.the_boring_developers.common.entity.sport_type.dto.CreateSportTypeDto;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;

import java.util.List;

public interface SportTypeService {
    List<GetSportTypeDto> findByUserId(Long userId);
    List<GetSportTypeDto> findByTeamId(Long teamId);
    List<GetSportTypeDto> findAll();
    void create(CreateSportTypeDto createSportTypeDto);
}
