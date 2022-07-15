package ru.the_boring_developers.common.repository.user_sport_type_link;

import ru.the_boring_developers.common.entity.user.link.UserSportTypeLink;

public interface UserSportTypeLinkRepository {

    void create(UserSportTypeLink userSportTypeLink);
    void delete(Long userId);
}
