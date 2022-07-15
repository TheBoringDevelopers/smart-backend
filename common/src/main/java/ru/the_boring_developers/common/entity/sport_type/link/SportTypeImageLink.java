package ru.the_boring_developers.common.entity.sport_type.link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class SportTypeImageLink {
    private Long sportTypeId;
    private Long imageId;
}
