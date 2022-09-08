package io.openapiprocessor.samples.validations;

import io.openapiprocessor.openapi.model.Bar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BarSumValidator implements ConstraintValidator<Sum, Bar> {
    private Integer sum;

    @Override
    public void initialize (Sum constraintAnnotation) {
        sum = constraintAnnotation.value ();
    }

    @Override
    public boolean isValid (Bar value, ConstraintValidatorContext context) {
        return value.getBar1 () + value.getBar2 () == sum;
    }
}
