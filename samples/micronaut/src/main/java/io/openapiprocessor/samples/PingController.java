/*
 * Copyright 2021 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import io.micronaut.http.annotation.Controller;
import io.openapiprocessor.api.PingApi;

@Controller
public class PingController implements PingApi {

    @Override
    public String getPing () {
        return "pong";
    }

}
