package ru.the_boring_developers.common.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private Long age;
    private String city;
    private String description;
    private boolean gender;
    private OffsetDateTime passwordChanged;
    private OffsetDateTime loginTime;
}
