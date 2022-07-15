package ru.the_boring_developers.api.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.api.service.sport_type.SportTypeService;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.entity.user.dto.GetUserDto;
import ru.the_boring_developers.common.entity.user.dto.UpdateUserDto;
import ru.the_boring_developers.common.entity.user.link.UserSportTypeLink;
import ru.the_boring_developers.common.exception.DomainException;
import ru.the_boring_developers.common.repository.image.ImageRepository;
import ru.the_boring_developers.common.repository.sport_type.SportTypeRepository;
import ru.the_boring_developers.common.repository.user.UserRepository;
import ru.the_boring_developers.common.repository.user_sport_type_link.UserSportTypeLinkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SportTypeService sportTypeService;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserSportTypeLinkRepository userSportTypeLinkRepository;
    private final SportTypeRepository sportTypeRepository;

    @Override
    public GetUserDto findDto(Long userId) {
        return collectDto(userRepository.findById(userId));
    }

    @Override
    public void update(UpdateUserDto user) {
        userRepository.update(user.getUser());
        userSportTypeLinkRepository.delete(user.getUser().getId());
        for (SportType sportType : user.getSportTypeList()) {
            userSportTypeLinkRepository.create(
                    UserSportTypeLink.builder()
                            .userId(user.getUser().getId())
                            .sportTypeId(sportTypeRepository.findByName(sportType.getName()).getId())
                            .build()
            );
        }
    }

    @Override
    public Long create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.create(user);
    }

    @Override
    public User find(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<GetUserDto> findByTeamId(Long teamId) {
        List<User> users = userRepository.findByTeamId(teamId);
        return users.stream().map(this::collectDto).collect(Collectors.toList());
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User userInfo = userRepository.findById(userId);
        if (userInfo == null) {
            throw new DomainException("Пользователь не найден");
        }
        String oldPasswordHash = passwordEncoder.encode(oldPassword);
        String userPasswordHash = userInfo.getPassword();
        if (!oldPasswordHash.equals(userPasswordHash)) {
            throw new DomainException("Неверный старый пароль");
        }
        return userRepository.updatePassword(userId, passwordEncoder.encode(newPassword));
    }

    @Override
    public boolean isLoginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public List<GetUserDto> findDtos(Long userId) {
        List<User> users = userRepository.findWithoutTeam();
        return users.stream().map(this::collectDto).collect(Collectors.toList());
    }

    private GetUserDto collectDto(User user) {
        List<GetSportTypeDto> sportTypes = sportTypeService.findByUserId(user.getId());
        Image image = imageRepository.findByUserId(user.getId());
        return GetUserDto.map(user, sportTypes, image);
    }
}
