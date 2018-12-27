package be.wouterversyck.slackintegration.web.facades;

import be.wouterversyck.slackintegration.model.common.User;
import be.wouterversyck.slackintegration.services.apiServices.GeekJokesService;
import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.viewModels.SlackFunFactMessageConverter;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static be.wouterversyck.slackintegration.model.slack.Action.ActionValues.UPVOTE;

@Service
public class SlackFacade {
    private FunFactService funFactService;
    private SlackFunFactMessageConverter messageConverter;

    public SlackFacade(final FunFactService funFactService, final SlackFunFactMessageConverter messageConverter) {
        this.funFactService = funFactService;
        this.messageConverter = messageConverter;
    }

    public Mono<Message> getSlackFunFact() {
        return funFactService.getRandom()
                .map(messageConverter::fromFunFact);
    }

    public Mono<Message> vote(final ActionResponse actionResponse) {
        return Flux.fromIterable(actionResponse.getActions())
                .filter(e -> e.getName().equals("appraisal"))
                .next()
                .flatMap(e -> vote(actionResponse.getCallbackId(), e.getValue(), actionResponse.getUser()));
    }

    private Mono<Message> vote(final String id, final String value, final User user) {
        Mono<FunFact> message;
        if(value.equals(UPVOTE.getValue())) {
             message = funFactService.upvote(id, user);
        } else {
            message = funFactService.downVote(id, user);
        }

        return message.map(messageConverter::fromFunFact);
    }
}
