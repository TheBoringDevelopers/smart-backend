package ru.the_boring_developers.common.entity.user.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.user.User;

import java.util.List;


@Getter
@Setter
@ToString
public class RegistrationInfo {

    private String sessionId;
    private String confirmationCode;
    private User userInfo;
    private List<SportType> sportTypeList;
}
