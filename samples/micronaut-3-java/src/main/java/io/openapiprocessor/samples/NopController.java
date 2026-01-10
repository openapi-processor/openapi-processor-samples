/*
 * Copyright 2025 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import io.micronaut.http.annotation.Controller;
import io.openapiprocessor.openapi.api.NopApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class NopController implements NopApi {
    private static Logger log = LoggerFactory.getLogger(NopController.class);

    @Override
    public void getNop() {
        log.info("NopController.getNop()");
    }
}
