package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.BarApi;
import io.openapiprocessor.openapi.api.FooApi;
import io.openapiprocessor.openapi.model.Foo;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ApiController implements FooApi, BarApi {

    @Override
    public MappedBar postBar(MappedBar bar) {
        return bar != null ? bar : new MappedBar();
    }

    @Override
    public Foo postFoo(Foo foo) {
        return foo != null ? foo : new Foo("foo", UUID.randomUUID(), new MappedBar());
    }
}
