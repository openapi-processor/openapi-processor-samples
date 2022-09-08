package io.openapiprocessor.samples.validations;

import io.openapiprocessor.openapi.model.Foo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FooSumValidator implements ConstraintValidator<Sum, Foo> {
    private Integer sum;

    @Override
    public void initialize (Sum constraintAnnotation) {
        sum = constraintAnnotation.value ();
    }

    @Override
    public boolean isValid (Foo value, ConstraintValidatorContext context) {
        return value.getFoo1 () + value.getFoo2 () == sum;
    }
}
