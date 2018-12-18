package com.poc.slackbotpoc.services;

import com.poc.slackbotpoc.model.FunFact;
import com.poc.slackbotpoc.repositories.FunFactRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FunFactService {

    private FunFactRepository repository;

    public FunFactService(@NonNull final FunFactRepository repository) {
        this.repository = repository;
    }

    public Mono<FunFact> add(@NonNull final FunFact funFact) {
        return repository.save(funFact);
    }

    public Mono<FunFact> get(@NonNull final String id) {
        return repository.findById(id);
    }
}
