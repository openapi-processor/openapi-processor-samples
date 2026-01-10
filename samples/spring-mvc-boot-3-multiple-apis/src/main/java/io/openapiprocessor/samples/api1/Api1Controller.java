package io.openapiprocessor.samples.api1;

import io.openapiprocessor.openapi1.api.Api;
import io.openapiprocessor.openapi1.model.Foo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api1Controller implements Api {

    @Override
    public Foo postApi1Foo(Type type, Foo foo) {
        var result = new Foo();
        result.setId(foo.getId());
        result.setType(type);
        return result;
    }
}
