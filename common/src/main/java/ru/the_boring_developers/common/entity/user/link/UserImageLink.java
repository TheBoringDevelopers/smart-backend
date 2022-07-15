package ru.the_boring_developers.common.entity.user.link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserImageLink {
    private Long userId;
    private Long imageId;
}
