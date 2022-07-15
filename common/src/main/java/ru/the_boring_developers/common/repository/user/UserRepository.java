package ru.the_boring_developers.common.repository.user;


import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository {
    User findByPhone(String phone);
    User findById(Long id);
    List<User> findByTeamId(Long teamId);
    List<User> findWithoutTeam();
    boolean updatePassword(Long id, String password);
    boolean existsByLogin(String login);
    Long create(User user);
    void updateLoginTime(Long userId);
    boolean update(User user);
}
