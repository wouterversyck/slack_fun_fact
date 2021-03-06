package be.wouterversyck.slackintegration.web.viewModels;

import be.wouterversyck.slackintegration.model.common.User;
import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.funFact.Vote;
import be.wouterversyck.slackintegration.model.geekJokes.Joke;
import be.wouterversyck.slackintegration.model.slack.Action;
import be.wouterversyck.slackintegration.model.slack.Attachment;
import be.wouterversyck.slackintegration.model.slack.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class SlackFunFactMessageConverter {

    public Message fromFunFact(final FunFact funFact, final User user) {
        return Message.builder()
                .withText(funFact.getTitle())
                .withResponseType(Message.ResponseType.IN_CHANNEL)
                .withAttachment(
                        Attachment.builder()
                                .withText(funFact.getFunFact())
                                .withCallbackId(funFact.getId())
                                .withAuthorName(funFact.getAuthor())
                                .withTimestamp(funFact.getCreateDateUnix())
                                .withColor(Attachment.Color.GREEN)
                                .withFooter(format("Votes: %s", funFact.getVoteCount()))
                                .withActions(getActions(funFact, user))
                                .build()
                ).build();
    }

    public Message fromJoke(final Joke joke) {
        return Message.builder()
                .withText(joke.getTitle() == null || joke.getTitle().isEmpty() ? "Here's a fun fact" : joke.getTitle())
                .withResponseType(Message.ResponseType.IN_CHANNEL)
                .withAttachment(
                        Attachment.builder()
                                .withText(joke.getJoke())
                                .withCallbackId("do_nothing")
                                .withTimestamp(new Date().getTime()/1000)
                                .withColor(Attachment.Color.GREEN)
                                .withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Was this dank?")
                                                .withValue("up")
                                                .build()
                                ).withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Not so dank")
                                                .withValue("down")
                                                .build()
                        ).build()
                ).build();
    }

    private List<Action> getActions(FunFact funFact, final User user) {
        List<Action> actions = new ArrayList<>();
        Optional<Vote> vote = funFact.userHasVoted(user);

        if(vote.isPresent()) {
            if(vote.get().isUpVote()) {
                actions.add(getRemoveUpVoteAction());
                return actions;
            }
            actions.add(getRemoveDownVoteAction());
            return actions;
        }
        actions.add(getUpVoteAction());
        actions.add(getDownVoteAction());
        return actions;
    }

    private Action getUpVoteAction() {
        return Action.builder()
                .withName("appraisal")
                .withType(Action.ActionType.BUTTON)
                .withText("Dank!")
                .withValue(Action.ActionValue.UP_VOTE.name())
                .build();
    }

    private Action getRemoveUpVoteAction() {
        return Action.builder()
                .withName("appraisal")
                .withType(Action.ActionType.BUTTON)
                .withText("Remove up vote")
                .withValue(Action.ActionValue.UP_VOTE.name())
                .build();
    }

    private Action getDownVoteAction() {
        return Action.builder()
                .withName("appraisal")
                .withType(Action.ActionType.BUTTON)
                .withText("Not so dank")
                .withValue(Action.ActionValue.DOWN_VOTE.name())
                .build();
    }

    private Action getRemoveDownVoteAction() {
        return Action.builder()
                .withName("appraisal")
                .withType(Action.ActionType.BUTTON)
                .withText("Remove down vote")
                .withValue(Action.ActionValue.DOWN_VOTE.name())
                .build();
    }
}
