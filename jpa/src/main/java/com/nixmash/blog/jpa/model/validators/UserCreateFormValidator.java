package com.nixmash.blog.jpa.model.validators;

import com.nixmash.blog.jpa.dto.UserDTO;
import com.nixmash.blog.jpa.model.User;
import com.nixmash.blog.jpa.service.AccessService;
import com.nixmash.blog.jpa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreateFormValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateFormValidator.class);
    private final UserService userService;
    private final AccessService accessService;

    @Autowired
    public UserCreateFormValidator(UserService userService, AccessService accessService) {
        this.userService = userService;
        this.accessService = accessService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("Validating {}", target);
        UserDTO form = (UserDTO) target;
        if (form.isNew()) {
            validatePasswords(errors, form);
            validateEmail(errors, form);
            validateDomain(errors, form);
            validateUsername(errors, form);
        } else {
            validateUsername(errors, form, form.getUserId());
        }
    }

    private void validatePasswords(Errors errors, UserDTO form) {
        if (!form.getPassword().equals(form.getRepeatedPassword())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserDTO form) {
        if (userService.getByEmail(form.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }

    private void validateDomain(Errors errors, UserDTO form) {
        if (!accessService.isEmailApproved(form.getEmail())) {
            errors.reject("domain.not.approved",
                    "The email address domain is not currently supported");
        }
    }

    private void validateUsername(Errors errors, UserDTO form) {
        if (userService.getUserByUsername(form.getUsername()) != null) {
            errors.reject("user.exists", "User with this username already exists");
        }
    }

    private void validateUsername(Errors errors, UserDTO form, long userId) {
        User user = userService.getUserByUsername(form.getUsername());
        if (user != null) {
            if (user.getId() != userId) {
                errors.reject("user.exists", "User with this username already exists");
            }
        }
    }
}
