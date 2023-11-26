package io.openapiprocessor.samples.api1;

import io.openapiprocessor.openapi1.api.Api;
import io.openapiprocessor.openapi1.model.Foo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api1Controller implements Api {

    @Override
    public Foo postFoo(String type, Foo body) {
        return null;
    }
}
