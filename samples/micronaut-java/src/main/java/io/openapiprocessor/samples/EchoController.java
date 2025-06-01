/*
 * Copyright 2021 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import io.micronaut.http.annotation.Controller;
import io.openapiprocessor.openapi.api.EchoApi;

@Controller
public class EchoController implements EchoApi {

    @Override
    public String getEcho (String source) {
        return source;
    }
}
