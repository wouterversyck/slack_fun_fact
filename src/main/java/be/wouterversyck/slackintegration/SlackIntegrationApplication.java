package be.wouterversyck.slackintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class SlackIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackIntegrationApplication.class, args);
    }

}

