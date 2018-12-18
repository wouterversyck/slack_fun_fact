package com.poc.slackbotpoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
        "classpath:properties/application.properties",
        "classpath:properties/application-${env}.properties"
})
@Configuration
public class PropertiesSourceConfig {
}
