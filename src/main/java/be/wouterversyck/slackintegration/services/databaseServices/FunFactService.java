package be.wouterversyck.slackintegration.services.databaseServices;

import be.wouterversyck.slackintegration.model.common.User;
import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.funFact.Vote;
import be.wouterversyck.slackintegration.repositories.FunFactRepository;
import lombok.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    public Mono<FunFact> save(@NonNull final FunFact funFact) {
        return repository.save(funFact);
    }

    public Mono<FunFact> upvote(@NonNull final FunFact funFact, @NonNull final User user) {
        funFact.getVotes().add(createVote(true, user));
        return repository.save(funFact);
    }

    public Mono<FunFact> downVote(@NonNull final FunFact funFact, @NonNull final User user) {
        funFact.getVotes().add(createVote(false, user));
        return repository.save(funFact);
    }

    public Mono<FunFact> upvote(@NonNull final String id, @NonNull final User user) {
        return get(id)
                .doOnNext(e -> e.getVotes().add(createVote(true, user)))
                .flatMap(this::save);
    }

    public Mono<FunFact> downVote(@NonNull final String id, @NonNull final User user) {
        return get(id)
                .doOnNext(e -> e.getVotes().add(createVote(false, user)))
                .flatMap(this::save);
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
        return repository.getRandom();
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_top_voted")
    public Mono<FunFact> getTopVoted() {
        return repository.findTopByOrderByVotesDesc();
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_latest")
    public Mono<FunFact> getLatest() {
        return repository.findTopByOrderByCreateDateDesc();
    }

    private Vote createVote(boolean upVote, User user) {
        return Vote.builder()
                .withVote(upVote)
                .withUser(user)
                .build();
    }
}
