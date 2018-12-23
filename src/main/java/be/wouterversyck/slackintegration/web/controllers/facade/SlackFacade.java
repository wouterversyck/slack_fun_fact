package be.wouterversyck.slackintegration.web.controllers.facade;

import be.wouterversyck.slackintegration.externalServices.GeekJokesService;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.model.slack.MessageConverter;
import be.wouterversyck.slackintegration.services.FunFactService;
import org.springframework.stereotype.Service;
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
        boolean useJokeService = Math.floor(Math.random() * 2) == 0;

        if(useJokeService) {
            return geekJokesService.getJoke()
                    .map(MessageConverter::fromJoke);
        }

        return funFactService.getLatest()
                .map(MessageConverter::fromFunFact);
    }
}
