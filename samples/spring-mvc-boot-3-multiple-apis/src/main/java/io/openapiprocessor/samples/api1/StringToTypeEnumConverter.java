package io.openapiprocessor.samples.api1;

import org.springframework.core.convert.converter.Converter;

/**
 * custom enum mapper to map the OpenAPI enum value to a different enum Java value
 */
class StringToTypeEnumConverter implements Converter<String, Type> {
    public Type convert(String source) {
        return Type.fromValue(source);
    }
}
