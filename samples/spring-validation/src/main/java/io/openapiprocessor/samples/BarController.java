package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.BarApi;
import io.openapiprocessor.openapi.model.Bar;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class BarController implements BarApi {

    @Override
    public Bar postBar (Bar bar) {
        return bar;
    }
}
