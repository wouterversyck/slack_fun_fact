package be.wouterversyck.slackintegration.services;

import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.repositories.FunFactRepository;
import lombok.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class FunFactService {

    private FunFactRepository repository;

    public FunFactService(@NonNull final FunFactRepository repository) {
        this.repository = repository;
    }

    public Mono<FunFact> add(@NonNull final FunFact funFact) {
        return repository.save(funFact);
    }

    public Mono<FunFact> save(@NonNull final FunFact funFact) {
        return repository.save(funFact);
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_one")
    public Mono<FunFact> get(@NonNull final String id) {
        return repository.findById(id);
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_all")
    public Flux<FunFact> getAll() {
        return repository.findAll();
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_random")
    public Mono<FunFact> getRandom() {
        throw new NotImplementedException();
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_latest")
    public Mono<FunFact> getLatest() {
        return repository.findTopByOrderByCreateDateDesc();
    }
}
