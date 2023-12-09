package com.oskarlund.musicapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springdoc.core.Constants.CLASSPATH_RESOURCE_LOCATION;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    public static final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    private final ResourceProperties resourceProperties;

    public SwaggerConfig(ResourceProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        LOG.debug("*** CLASSPATH_RESOURCE_LOCATION = {}", CLASSPATH_RESOURCE_LOCATION);
        LOG.debug("*** resourceProperties.getStaticLocations() = {}", Arrays.toString(resourceProperties.getStaticLocations()));

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATION);
        registry.addResourceHandler("**")
                .addResourceLocations(resourceProperties.getStaticLocations());
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("/webjars/");
    }
}
