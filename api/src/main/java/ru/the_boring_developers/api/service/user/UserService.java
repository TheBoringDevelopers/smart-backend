package ru.the_boring_developers.api.service.user;

import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.entity.user.dto.GetUserDto;
import ru.the_boring_developers.common.entity.user.dto.UpdateUserDto;

import java.util.List;

public interface UserService {
    Long create(User user);
    User find(Long userId);
    List<GetUserDto> findByTeamId(Long teamId);
    User findByPhone(String phone);
    GetUserDto findDto(Long userId);
    void update(UpdateUserDto user);
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
    boolean isLoginExists(String login);
    List<GetUserDto> findDtos(Long userId);
}
