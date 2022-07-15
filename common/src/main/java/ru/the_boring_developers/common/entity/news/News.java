package ru.the_boring_developers.common.entity.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private Long id;
    private String title;
    private String description;
    private OffsetDateTime created;
}
