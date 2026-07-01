package com.circle_devs.zdanovskih.entity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;
/**
 * Class for check print year
 */
public class NotInFutureYearValidator implements ConstraintValidator<NotInFutureYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;

        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
}
