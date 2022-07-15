package ru.the_boring_developers.common.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.user.User;

import java.util.List;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private User user;
    private List<SportType> sportTypeList;
}
