package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.FunFact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FunFactRepository extends ReactiveCrudRepository<FunFact, String> {

    Mono<FunFact> findTopByOrderByCreateDateDesc();
}
