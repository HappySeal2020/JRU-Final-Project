package com.javarush.zdanovskih.entity;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotInFutureYearValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInFutureYear {

    String message() default "Print year can not be in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}