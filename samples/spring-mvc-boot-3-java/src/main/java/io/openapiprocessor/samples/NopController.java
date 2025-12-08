package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.NopApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NopController implements NopApi {
    private static final Logger log = LoggerFactory.getLogger(NopController.class);

    @Override
    public void getNop() {
        log.info("NopController.getNop()");
    }
}
