package com.poc.slackbotpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class SlackBotPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackBotPocApplication.class, args);
    }

}

