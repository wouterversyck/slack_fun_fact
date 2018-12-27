package be.wouterversyck.slackintegration.web.controllers;

import be.wouterversyck.slackintegration.model.slack.ActionResponse;
import be.wouterversyck.slackintegration.model.slack.Message;
import be.wouterversyck.slackintegration.web.facades.SlackFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
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

    @RequestMapping(value = "/vote", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> vote(@RequestBody MultiValueMap<String, String> payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ActionResponse actionResponse = mapper.readValue(payload.getFirst("payload"), ActionResponse.class);
        return slackFacade.vote(actionResponse)
                .map(e -> ok().body(e));
    }
}
