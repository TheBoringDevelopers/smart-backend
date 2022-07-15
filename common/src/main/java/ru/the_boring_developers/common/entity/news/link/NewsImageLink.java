package ru.the_boring_developers.common.entity.news.link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewsImageLink {
    private Long newsId;
    private Long imageId;
}
