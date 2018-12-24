package be.wouterversyck.slackintegration.web.controllers.facade;

import be.wouterversyck.slackintegration.externalServices.GeekJokesService;
import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.controllers.viewModels.SlackFunFactMessageCreator;
import be.wouterversyck.slackintegration.services.FunFactService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SlackFacade {
    private FunFactService funFactService;
    private GeekJokesService geekJokesService;

    public SlackFacade(final FunFactService funFactService, final GeekJokesService geekJokesService) {
        this.funFactService = funFactService;
        this.geekJokesService = geekJokesService;
    }

    public Mono<Message> getSlackFunFact() {
        //TODO store external objects in db with a hash
//        boolean useJokeService = Math.floor(Math.random() * 2) == 0;
//
//        if(useJokeService) {
//            return geekJokesService.getJoke()
//                    .map(SlackFunFactMessageCreator::fromJoke);
//        }

        return funFactService.getLatest()
                .map(SlackFunFactMessageCreator::fromFunFact);
    }

    public Mono<FunFact> vote(final ActionResponse actionResponse) {
        return Flux.fromIterable(actionResponse.getActions())
                .filter(e -> e.getName().equals("appraisal"))
                .next()
                .flatMap(e -> vote(actionResponse.getCallbackId(), e.getValue()));
    }

    private Mono<FunFact> vote(final String id, final String value) {
        return funFactService.get(id)
                .doOnNext(e -> e.setVotes(e.getVotes() + 1))
                .flatMap(funFactService::save);
    }
}
