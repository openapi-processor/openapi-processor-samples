package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.api.PingApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of the ping api defined in src/api/openapi.yaml.
 */
@RestController
public class PingController implements PingApi {

    @Override
    public String getPing () {
        return "pong";
    }

}
