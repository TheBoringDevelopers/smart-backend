package ru.the_boring_developers.api.service.team;

import ru.the_boring_developers.common.entity.team.dto.CreateTeamDto;
import ru.the_boring_developers.common.entity.team.dto.GetTeamDto;

import java.util.List;

public interface TeamService {
    GetTeamDto find(Long userId);
    List<GetTeamDto> find(Long userId, List<Long> sportTypes, Boolean empty);
    void delete(Long userId, Long teamId);
    Long create(Long userId, CreateTeamDto createTeamDto);
    void delete(Long userId, Long requestedUserId, Long teamId);
    void leave(Long userId, Long teamId);
    void invite(Long userId, Long teamId, Long requestedUserId);
}
