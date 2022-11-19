package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.EchoApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of the echo api defined in src/api/openapi.yaml.
 */
@RestController
public class EchoController implements EchoApi {

    @Override
    public String getEcho (String source) {
        return source;
    }

}
