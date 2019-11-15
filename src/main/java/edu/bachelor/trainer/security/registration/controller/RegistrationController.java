package edu.bachelor.trainer.security.registration.controller;

import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.exceptions.UserNameExistsException;
import edu.bachelor.trainer.security.registration.services.RegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final
    RegisterService registerService;

    public RegistrationController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(value = "/user")
    public Response registerUserAccount(@RequestBody UserDto accountDto) {
        User registered = createUserAccount(accountDto);
        if (registered == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            return Response.status(Response.Status.OK).build();
        }
    }

    private User createUserAccount(UserDto accountDto) {
        User registered;
        try {
            registered = registerService.registerNewUserAccount(accountDto);
        } catch (UserNameExistsException e) {
            return null;
        }
        return registered;
    }
}
