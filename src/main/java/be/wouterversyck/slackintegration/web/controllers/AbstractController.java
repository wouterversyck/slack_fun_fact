package be.wouterversyck.slackintegration.web.controllers;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

public abstract class AbstractController <T> {

    Mono<ResponseEntity<T>> onEmptyNotFound(Mono<T> mono) {
        return mono.map(e -> ok().body(e))
                .defaultIfEmpty(notFound().build());
    }
}
