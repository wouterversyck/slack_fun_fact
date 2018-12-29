package be.wouterversyck.slackintegration.services.databaseServices;

import be.wouterversyck.slackintegration.exceptions.FunFactNotFoundException;
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

    /**
     * If there is already an upVote by the user ? remove upVote : add upVote
     * @param funFactId
     * @param user
     * @return
     */
    public Mono<FunFact> upVoteOrRemoveUpVote(@NonNull final String funFactId, @NonNull final User user) {
        return getRemoveVoteMono(funFactId, user.getId())
                .switchIfEmpty(
                        onEmptyThrow(get(funFactId))
                                .doOnNext(e -> e.getVotes().add(createVote(true, user)))
                                .flatMap(this::save)
                );
    }

    /**
     * If there is already an downVote by the user ? remove downVote : add downVote
     * @param funFactId
     * @param user
     * @return
     */
    public Mono<FunFact> downVoteOrRemoveDownVote(@NonNull final String funFactId, @NonNull final User user) {
        return getRemoveVoteMono(funFactId, user.getId())
                .switchIfEmpty(
                        onEmptyThrow(get(funFactId))
                            .doOnNext(e -> e.getVotes().add(createVote(false, user)))
                            .flatMap(this::save)
                );
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_one")
    public Mono<FunFact> get(@NonNull final String funFactId) {
        return onEmptyThrow(repository.findById(funFactId));
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_all")
    public Flux<FunFact> getAll() {
        return repository.findAll();
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_random")
    public Mono<FunFact> getRandom() {
        return onEmptyThrow(repository.getRandom());
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_top_voted")
    public Mono<FunFact> getTopVoted() {
        return onEmptyThrow(repository.findTopByOrderByVotesDesc());
    }

    @Cacheable("be.wouterversyck.slack-integration.fun_fact.get_latest")
    public Mono<FunFact> getLatest() {
        return onEmptyThrow(repository.findTopByOrderByCreateDateDesc());
    }

    private Vote createVote(boolean upVote, User user) {
        return Vote.builder()
                .withVote(upVote)
                .withUser(user)
                .build();
    }

    private Mono<FunFact> getRemoveVoteMono(String funFactId, String userId) {
        return repository.getVote(funFactId, userId)
                .flatMap(e ->
                        get(funFactId)
                                .flatMap(x -> {
                                    x.getVotes().remove(e);
                                    return save(x);
                                }));
    }

    private <T> Mono<T> onEmptyThrow(Mono<T> mono) {
        return mono.switchIfEmpty(
                Mono.defer(() -> Mono.error(new FunFactNotFoundException("Fun fact not found")))
        );
    }
}
