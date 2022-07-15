package ru.the_boring_developers.auth.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.exception.DomainException;
import ru.the_boring_developers.common.repository.user.UserRepository;

import java.time.OffsetDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceAuthImpl implements UserServiceAuth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.create(user);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUser(Long userId, String organizationId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public boolean updatePassword(Long userId, String password) {
        return userRepository.updatePassword(userId, passwordEncoder.encode(password));
    }

    @Override
    public boolean updatePassword(String login, String password) {
        User user = userRepository.findByPhone(login);
        if (user != null) {
            return userRepository.updatePassword(user.getId(), passwordEncoder.encode(password));
        }
        throw new DomainException("Логин " + login + " не зарегестрирован");
    }

    @Override
    public boolean isLoginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Long getUserIdByLogin(String login) {
        User user = getUserByPhone(login);
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }

    @Override
    public String getPassword(Long userId) {
        User user = getUser(userId);
        if (user != null) {
            return user.getPassword();
        } else {
            return "";
        }
    }

    @Override
    public OffsetDateTime getPasswordChanged(Long userId) {
        User user = getUser(userId);
        if (user != null) {
            return user.getPasswordChanged();
        } else {
            return null;
        }
    }


    @Override
    public void updateLoginTime(Long userId) {
        userRepository.updateLoginTime(userId);
    }
}
