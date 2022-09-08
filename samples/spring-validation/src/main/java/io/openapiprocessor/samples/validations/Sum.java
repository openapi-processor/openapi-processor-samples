package io.openapiprocessor.samples.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint (validatedBy = {FooSumValidator.class, BarSumValidator.class})
@Target ({ ElementType.TYPE, ElementType.PARAMETER })
@Retention (value = RetentionPolicy.RUNTIME)
public @interface Sum {
    String message () default "invalid sum";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};

    int value();
}
