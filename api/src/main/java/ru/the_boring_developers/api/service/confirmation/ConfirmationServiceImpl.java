package ru.the_boring_developers.api.service.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.the_boring_developers.api.service.user.UserService;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.entity.user.link.UserSportTypeLink;
import ru.the_boring_developers.common.entity.user.registration.Confirmation;
import ru.the_boring_developers.common.entity.user.registration.RegistrationInfo;
import ru.the_boring_developers.common.entity.user.registration.SessionWithCode;
import ru.the_boring_developers.common.exception.DomainException;
import ru.the_boring_developers.common.repository.confirmation.ConfirmationRepository;
import ru.the_boring_developers.common.repository.sport_type.SportTypeRepository;
import ru.the_boring_developers.common.repository.user_sport_type_link.UserSportTypeLinkRepository;
import ru.the_boring_developers.common.utils.Utils;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;

import static ru.the_boring_developers.common.utils.Utils.md5;

@Component
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationRepository confirmationRepository;
    private final UserService userService;
    private final UserSportTypeLinkRepository userSportTypeLinkRepository;
    private final SportTypeRepository sportTypeRepository;

    @Override
    public boolean isCorrectCode(String phone, String sessionId, String code) {
        Confirmation confirmation = confirmationRepository.getLast(phone);
        if (confirmation == null) {
            throw new EntityNotFoundException("Отсутствует информация для подтверждения номера " + phone);
        }
        return checkCode(confirmation, phone, sessionId, code);
    }

    @Override
    public void confirm(RegistrationInfo registrationInfo) {
        User user = registrationInfo.getUserInfo();

        if (!isCorrectCode(
                user.getPhoneNumber(),
                registrationInfo.getSessionId(),
                registrationInfo.getConfirmationCode()
        )) {
            throw new DomainException("Неверный код подтверждения");
        }

        if (userService.isLoginExists(user.getPhoneNumber())) {
            throw new DomainException("Пользователь уже зарегистрирован");
        }
        Long userId = userService.create(user);
        for (SportType sportType : registrationInfo.getSportTypeList()) {
            userSportTypeLinkRepository.create(
                    UserSportTypeLink.builder()
                            .userId(userId)
                            .sportTypeId(sportTypeRepository.findByName(sportType.getName()).getId())
                    .build()
            );
        }
    }

    @Override
    public SessionWithCode register(String phone) {
        User user = userService.findByPhone(phone);
        if (user != null) {
            throw new DomainException("Вы уже зарегистрированы. Произведите вход или восстановите пароль");
        }
        checkSmsToken(phone);
        String code = Utils.generateCode();
        String sessionId = Utils.generateSessionId();
        saveConfirmationCode(phone, sessionId, code);
        return SessionWithCode.builder()
                .code(code)
                .session(sessionId)
                .build();
    }

    private boolean checkCode(Confirmation confirmation, String phone, String sessionId, String code) {
        String confirmationCode = md5(phone + sessionId + code);
        if (!confirmationCode.equals(confirmation.getCode())) {
            return false;
        }
        confirmationRepository.deleteAll(confirmation.getLogin());
        return true;
    }


    private void checkSmsToken(String login) {
        Confirmation confirmation = confirmationRepository.getLast(login);
        if (confirmation != null) {
            confirmationRepository.deleteAll(login);
        }
    }

    private long saveConfirmationCode(String login, String sessionId, String code) {
        String confirmationCode = md5(login + sessionId + code);
        Confirmation confirmation = new Confirmation();
        confirmation.setLogin(login);
        confirmation.setCode(confirmationCode);
        confirmation.setSessionId(sessionId);
        confirmation.setCreated(OffsetDateTime.now());
        confirmation.setAttemptCount(0);
        return confirmationRepository.create(confirmation);
    }
}
