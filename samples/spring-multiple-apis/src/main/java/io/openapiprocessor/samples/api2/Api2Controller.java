package io.openapiprocessor.samples.api2;

import io.openapiprocessor.openapi2.api.Api;
import io.openapiprocessor.openapi2.model.Foo;
import io.openapiprocessor.openapi2.model.Type;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class Api2Controller implements Api {

    @Override
    public Foo postApi2Foo(Type type, Foo foo) {
        return null;
    }
}
