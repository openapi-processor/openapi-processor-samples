/*
 * Copyright 2020 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code = HttpStatus.NOT_FOUND)
public class UnknownBookException extends Exception {
}
