package ru.the_boring_developers.common.entity.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private Long id;
    private String name;
    private String city;
    private String description;
    private boolean organization;
    private String organizationName;
}
