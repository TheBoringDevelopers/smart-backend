package ru.the_boring_developers.common.entity.news.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.news.News;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetNewsDto {
    private News news;
    private Image image;
}
