package com.team5.project2.user.auth;

import com.team5.project2.user.dto.UserPostDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// @Component
public class UserInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserPostDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserPostDto userDto = (UserPostDto) target;

        if (!StringUtils.hasText(userDto.getName())) {
            errors.rejectValue("name", "required");
        }

        if (!StringUtils.hasText(userDto.getEmail())) {
            errors.rejectValue("email", "required");
        }

        if (!StringUtils.hasText(userDto.getPassword())) {
            errors.rejectValue("password", "required");
        }

        if (!StringUtils.hasText(userDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "required");
        }

        if (!StringUtils.hasText(userDto.getPhone_number())) {
            errors.rejectValue("phone_number", "required");
        }
    }
}