package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.FunFact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface FunFactRepository extends ReactiveCrudRepository<FunFact, String> {
}
