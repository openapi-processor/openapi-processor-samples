package io.openapiprocessor.samples.api1;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Api1WebConfig implements WebMvcConfigurer {

    @SuppressWarnings("rawtypes")
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTypeEnumConverter());
    }
}
