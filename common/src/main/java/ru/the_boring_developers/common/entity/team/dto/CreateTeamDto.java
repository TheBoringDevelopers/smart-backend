package ru.the_boring_developers.common.entity.team.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.team.Team;

import java.util.List;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDto {
    private Team team;
    private List<SportType> sportTypes;
}
