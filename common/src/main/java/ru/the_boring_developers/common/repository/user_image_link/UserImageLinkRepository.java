package ru.the_boring_developers.common.repository.user_image_link;

import ru.the_boring_developers.common.entity.user.link.UserImageLink;

public interface UserImageLinkRepository {

    void create(UserImageLink userImageLink);

    void delete(Long userId);
}
