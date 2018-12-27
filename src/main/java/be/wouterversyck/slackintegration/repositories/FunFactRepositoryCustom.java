package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.FunFact;
import reactor.core.publisher.Mono;

public interface FunFactRepositoryCustom {
    Mono<FunFact> getRandom();
}
