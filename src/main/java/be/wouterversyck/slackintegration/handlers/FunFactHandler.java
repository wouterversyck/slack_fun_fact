package be.wouterversyck.slackintegration.handlers;

import be.wouterversyck.slackintegration.model.errors.ValidationError;
import be.wouterversyck.slackintegration.services.FunFactService;
import be.wouterversyck.slackintegration.model.FunFact;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class FunFactHandler {
    private FunFactService funFactService;
    private Validator validator;

    public FunFactHandler(final FunFactService funFactService, Validator validator) {
        this.funFactService = funFactService;
        this.validator = validator;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FunFact.class)
                .flatMap(funFact -> {
                    Set<ConstraintViolation<FunFact>> errors = validator.validate(funFact);
                    if(errors.isEmpty()) {
                        return ok().body(
                                BodyInserters.fromPublisher(
                                    funFactService.add(funFact), FunFact.class
                                )
                        );
                    }
                    return badRequest().body(
                            BodyInserters.fromObject(
                                    constraintValidationToJson(errors)
                            )
                    );
                });
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return funFactService.get(serverRequest.pathVariable("id"))
                .flatMap(this::toResponse)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ok().body(
                BodyInserters.fromPublisher(
                        funFactService.getAll(), FunFact.class
                )
        );
    }

    public Mono<ServerResponse> getLatest(ServerRequest serverRequest) {
        return ok().body(
                BodyInserters.fromPublisher(
                        funFactService.getLatest(), FunFact.class
                )
        );
    }

    private Mono<ServerResponse> toResponse(final FunFact funFact) {
        return ok().body(BodyInserters.fromObject(funFact));
    }

    private Collection<ValidationError> constraintValidationToJson(Set<ConstraintViolation<FunFact>> errors) {
        return errors.stream()
                .map(ConstraintViolation::getMessage)
                .map(ValidationError::new)
                .collect(Collectors.toList());
    }
}
