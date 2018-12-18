package com.poc.slackbotpoc.repositories;

import com.poc.slackbotpoc.model.FunFact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface FunFactRepository extends ReactiveCrudRepository<FunFact, String> {
}
