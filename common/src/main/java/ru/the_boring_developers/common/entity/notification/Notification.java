package ru.the_boring_developers.common.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private NotificationType type;
    private OffsetDateTime created;
    private OffsetDateTime viewed;
}
