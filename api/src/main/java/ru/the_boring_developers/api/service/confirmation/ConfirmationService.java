package ru.the_boring_developers.api.service.confirmation;

import ru.the_boring_developers.common.entity.user.registration.RegistrationInfo;
import ru.the_boring_developers.common.entity.user.registration.SessionWithCode;

public interface ConfirmationService {

    boolean isCorrectCode(String phone, String sessionId, String code);

    void confirm(RegistrationInfo registrationInfo);

    SessionWithCode register(String phone);
}
