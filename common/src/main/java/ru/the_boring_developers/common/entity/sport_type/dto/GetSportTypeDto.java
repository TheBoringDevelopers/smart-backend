package ru.the_boring_developers.common.entity.sport_type.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.image.Image;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetSportTypeDto {
    private Long id;
    private String name;
    private Image image;
}
