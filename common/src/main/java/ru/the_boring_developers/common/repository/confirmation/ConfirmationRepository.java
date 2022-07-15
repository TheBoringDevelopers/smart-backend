package ru.the_boring_developers.common.repository.confirmation;

import ru.the_boring_developers.common.entity.user.registration.Confirmation;

public interface ConfirmationRepository {

    long create(Confirmation confirmation);
    Confirmation getLast(String login);
    void incrementAttemptCount(long confirmationId);
    boolean deleteAll(String login);
}
