package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.BarApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarController implements BarApi {

    @Override
    public MappedBar postBar(MappedBar bar) {
        return bar != null ? bar : new MappedBar();
    }
}
