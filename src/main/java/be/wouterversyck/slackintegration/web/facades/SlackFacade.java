package be.wouterversyck.slackintegration.web.facades;

import be.wouterversyck.slackintegration.services.apiServices.GeekJokesService;
import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.viewModels.SlackFunFactMessageCreator;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static be.wouterversyck.slackintegration.model.slack.Action.ActionValues.UPVOTE;

@Service
public class SlackFacade {
    private FunFactService funFactService;
    private GeekJokesService geekJokesService;

    public SlackFacade(final FunFactService funFactService, final GeekJokesService geekJokesService) {
        this.funFactService = funFactService;
        this.geekJokesService = geekJokesService;
    }

    public Mono<Message> getSlackFunFact() {
        return funFactService.getRandom()
                .map(SlackFunFactMessageCreator::fromFunFact);
    }

    public Mono<Message> vote(final ActionResponse actionResponse) {
        return Flux.fromIterable(actionResponse.getActions())
                .filter(e -> e.getName().equals("appraisal"))
                .next()
                .flatMap(e -> vote(actionResponse.getCallbackId(), e.getValue()));
    }

    private Mono<Message> vote(final String id, final String value) {
        Mono<FunFact> message;
        if(value.equals(UPVOTE.getValue())) {
             message = funFactService.upvote(id);
        } else {
            message = funFactService.downVote(id);
        }

        return message.map(SlackFunFactMessageCreator::fromFunFact);
    }
}
