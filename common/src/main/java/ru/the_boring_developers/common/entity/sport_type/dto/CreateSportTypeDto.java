package ru.the_boring_developers.common.entity.sport_type.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.sport_type.SportType;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateSportTypeDto {
    private SportType sportType;
    private Long imageId;
}
