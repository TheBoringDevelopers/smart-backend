package ru.the_boring_developers.common.entity.user.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Confirmation {

    private Long id;
    private String login;
    private String sessionId;
    private String code;
    private int attemptCount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime created = OffsetDateTime.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime changed = OffsetDateTime.now();
}
