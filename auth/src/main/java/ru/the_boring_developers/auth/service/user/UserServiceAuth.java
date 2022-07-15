package ru.the_boring_developers.auth.service.user;

import ru.the_boring_developers.common.entity.user.User;

import java.time.OffsetDateTime;

public interface UserServiceAuth {

    Long createUser(User user);
    User getUser(Long userId);
    User getUser(Long userId, String organizationId);
    User getUserByPhone(String login);
    boolean updatePassword(Long userId, String password);
    boolean updatePassword(String login, String password);
    boolean isLoginExists(String login);
    Long getUserIdByLogin(String login);
    String getPassword(Long userId);
    OffsetDateTime getPasswordChanged(Long userId);
    void updateLoginTime(Long userId);
}
