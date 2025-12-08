package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.FooApi;
import io.openapiprocessor.openapi.model.Foo;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class FooController implements FooApi {

    @Override
    public Foo postFoo(Foo foo) {
        return foo != null ? foo : new Foo("foo", UUID.randomUUID(), new MappedBar());
    }
}
