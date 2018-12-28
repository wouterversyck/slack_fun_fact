package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.funFact.Vote;
import reactor.core.publisher.Mono;

public interface FunFactRepositoryCustom {
    Mono<FunFact> getRandom();
    Mono<Vote> getVote(String funFactId, String userId);
}
