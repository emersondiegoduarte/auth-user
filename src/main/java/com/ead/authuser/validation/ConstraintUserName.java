package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConstraintUserNameImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstraintUserName {
    String message() default  "invalid username";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default  {};

}
