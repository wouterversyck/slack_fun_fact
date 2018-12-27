package be.wouterversyck.slackintegration.web.facades;

import be.wouterversyck.slackintegration.exceptions.InvalidActionValueException;
import be.wouterversyck.slackintegration.model.common.User;
import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.slack.Action;
import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.viewModels.SlackFunFactMessageConverter;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
public class SlackFacade {
    private FunFactService funFactService;
    private SlackFunFactMessageConverter messageConverter;

    public SlackFacade(final FunFactService funFactService, final SlackFunFactMessageConverter messageConverter) {
        this.funFactService = funFactService;
        this.messageConverter = messageConverter;
    }

    public Mono<Message> getSlackFunFact(final User user) {

        return funFactService.getRandom()
                .map(e -> messageConverter.fromFunFact(e, user));
    }

    public Mono<Message> vote(final ActionResponse actionResponse) {
        return Flux.fromIterable(actionResponse.getActions())
                .filter(e -> e.getName().equals("appraisal"))
                .next()
                .flatMap(e -> vote(actionResponse.getCallbackId(), e.getValue(), actionResponse.getUser()));
    }

    private Mono<Message> vote(final String id, final String value, final User user) throws InvalidActionValueException {
        Mono<FunFact> message;

        Action.ActionValue actionValue;
        try {
            actionValue = Action.ActionValue.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidActionValueException(format("%s is not a valid action", value));
        }

        switch (actionValue) {
            case UPVOTE : message = funFactService.upVote(id, user);
                break;
            case DOWNVOTE: message = funFactService.downVote(id, user);
                break;
            default:
                message = funFactService.get(id);
        }

        return message.map(e -> messageConverter.fromFunFact(e, user));
    }
}
