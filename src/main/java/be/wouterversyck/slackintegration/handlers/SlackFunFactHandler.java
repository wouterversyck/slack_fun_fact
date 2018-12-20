package be.wouterversyck.slackintegration.handlers;

import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.model.slack.SlackResponse;
import be.wouterversyck.slackintegration.services.FunFactService;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validator;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class SlackFunFactHandler {
    private FunFactService funFactService;
    private Validator validator;

    public SlackFunFactHandler(@NonNull final FunFactService funFactService, @NonNull final Validator validator) {
        this.funFactService = funFactService;
        this.validator = validator;
    }

    public Mono<ServerResponse> getRandom(ServerRequest serverRequest) {
        return funFactService.getRandom()
                .map(SlackResponse::fromFunFact)
                .flatMap(this::toResponse);
    }

    public Mono<ServerResponse> getLatest(ServerRequest serverRequest) {
        return funFactService.getLatest()
                .map(SlackResponse::fromFunFact)
                .flatMap(this::toResponse);
    }

    private Mono<ServerResponse> toResponse(final SlackResponse funFact) {
        return ok().body(BodyInserters.fromObject(funFact));
    }
}
