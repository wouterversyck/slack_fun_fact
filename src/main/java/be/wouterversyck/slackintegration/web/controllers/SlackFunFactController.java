package be.wouterversyck.slackintegration.web.controllers;

import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.controllers.facade.SlackFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/slack/funfact")
public class SlackFunFactController {
    private SlackFacade slackFacade;

    public SlackFunFactController(final SlackFacade slackFacade) {
        this.slackFacade = slackFacade;
    }

    @PostMapping
    public Mono<Message> getAFunFact() {
        return slackFacade.getSlackFunFact();
    }

    @PostMapping("/vote")
    public Mono<ResponseEntity> vote(final ActionResponse response) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
