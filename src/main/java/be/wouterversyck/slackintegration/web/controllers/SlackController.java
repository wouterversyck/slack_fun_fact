package be.wouterversyck.slackintegration.web.controllers;

import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.controllers.facade.SlackFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/slack")
public class SlackController {
    private SlackFacade slackFacade;

    public SlackController(final SlackFacade slackFacade) {
        this.slackFacade = slackFacade;
    }

    @PostMapping("/funfact")
    public Mono<Message> getAFunFact() {
        return slackFacade.getSlackFunFact();
    }
}
