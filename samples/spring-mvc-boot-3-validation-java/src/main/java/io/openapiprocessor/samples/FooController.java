package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.FooApi;
import io.openapiprocessor.openapi.model.Foo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class FooController implements FooApi {

    @Override
    public Foo postFoo (Foo foo) {
        return foo;
    }
}
