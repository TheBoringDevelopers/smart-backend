package ru.the_boring_developers.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.the_boring_developers.api.service.confirmation.ConfirmationService;
import ru.the_boring_developers.common.entity.user.registration.RegistrationInfo;
import ru.the_boring_developers.common.entity.user.registration.SessionWithCode;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final ConfirmationService confirmationService;

    @GetMapping(path = "/{phone}")
    public ResponseEntity<SessionWithCode> register(@PathVariable("phone") String phone) {
        return ok(confirmationService.register(phone));
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity confirmRegistration(@RequestBody RegistrationInfo registrationInfo) {
        confirmationService.confirm(registrationInfo);
        return ResponseEntity.ok().build();
    }
}
