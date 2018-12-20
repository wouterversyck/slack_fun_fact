package be.wouterversyck.slackintegration.config;

import be.wouterversyck.slackintegration.handlers.SlackFunFactHandler;
import be.wouterversyck.slackintegration.handlers.FunFactHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> funFactRoutes(FunFactHandler funFactHandler) {
        return route()
                .GET("/funfact/latest", funFactHandler::getLatest)
                .GET("/funfact/{id}", funFactHandler::get)
                .GET("/funfact", funFactHandler::getAll)
                .POST("/funfact", accept(APPLICATION_JSON), funFactHandler::add)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> greetingRoutes() {
        return route()
                .GET("/greeting", e ->
                    e.principal()
                            .map(Principal::getName)
                            .flatMap(p -> okResponse(format("Hello %s", p)))
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> slackRoutes(SlackFunFactHandler slackFunFactHandler) {
        return route()
                .GET("/slack/funfact", slackFunFactHandler::getAFunFact)
                .POST("/slack/funfact", slackFunFactHandler::getAFunFact)
                .build();
    }

    private Mono<ServerResponse> okResponse(Object object) {
        return ServerResponse.ok()
                .body(BodyInserters.fromObject(object));
    }
}
