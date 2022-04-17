package com.ead.authuser.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ConstraintUserNameImpl implements ConstraintValidator<ConstraintUserName, String> {


    @Override
    public void initialize(ConstraintUserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(Objects.isNull(username) || username.trim().isEmpty() || username.trim().contains(" ")){
            return false;
        }
        return true;
    }
}
