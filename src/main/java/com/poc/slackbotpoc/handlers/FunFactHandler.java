package com.poc.slackbotpoc.handlers;

import com.poc.slackbotpoc.model.FunFact;
import com.poc.slackbotpoc.services.FunFactService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class FunFactHandler {
    private FunFactService funFactService;

    public FunFactHandler(final FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FunFact.class)
                .doOnNext(funFactService::add)
                .then(ok().build());
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return funFactService.get(serverRequest.pathVariable("id"))
                .flatMap(this::toResponse)
                .switchIfEmpty(notFound().build());
    }

    private Mono<ServerResponse> toResponse(final FunFact funFact) {
        return ok().body(BodyInserters.fromObject(funFact));
    }
}
