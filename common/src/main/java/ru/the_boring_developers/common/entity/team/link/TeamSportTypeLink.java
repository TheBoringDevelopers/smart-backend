package ru.the_boring_developers.common.entity.team.link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeamSportTypeLink {
    private Long teamId;
    private Long sportTypeId;
}
