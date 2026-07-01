package com.circle_devs.zdanovskih.entity;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Interface for check print year
 */
@Documented
@Constraint(validatedBy = NotInFutureYearValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInFutureYear {

    String message() default "Print year can not be in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}