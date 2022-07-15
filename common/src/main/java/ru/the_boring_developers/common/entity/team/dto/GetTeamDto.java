package ru.the_boring_developers.common.entity.team.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;
import ru.the_boring_developers.common.entity.team.Team;
import ru.the_boring_developers.common.entity.user.dto.GetUserDto;

import java.util.List;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamDto {
    private Team team;
    private List<GetUserDto> users;
    private List<GetSportTypeDto> sportTypes;
}