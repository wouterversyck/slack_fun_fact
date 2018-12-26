package be.wouterversyck.slackintegration.web.controllers.viewModels;

import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.model.geekJokes.Joke;
import be.wouterversyck.slackintegration.model.slack.Action;
import be.wouterversyck.slackintegration.model.slack.Attachment;
import be.wouterversyck.slackintegration.model.slack.Message;

import java.util.Date;

import static java.lang.String.format;

public class SlackFunFactMessageCreator {

    public static Message fromFunFact(final FunFact funFact) {
        return Message.builder()
                .withText(funFact.getTitle())
                .withResponseType(Message.ResponseType.IN_CHANNEL)
                .withAttachment(
                        Attachment.builder()
                                .withText(funFact.getFunFact())
                                .withCallbackId(funFact.getId())
                                .withAuthorName(funFact.getAuthor())
                                .withTimestamp(funFact.getCreateDate().getTime())
                                .withColor(Attachment.Color.GREEN)
                                .withFooter(format("Votes: %s", funFact.getVotes()))
                                .withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Was this dank?")
                                                .withValue(Action.ActionValues.UPVOTE.getValue())
                                                .build()
                                ).withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Not so dank")
                                                .withValue(Action.ActionValues.DOWNVOTE.getValue())
                                                .build()
                        ).build()
                ).build();
    }

    public static Message fromJoke(final Joke joke) {
        return Message.builder()
                .withText(joke.getTitle() == null || joke.getTitle().isEmpty() ? "Here's a fun fact" : joke.getTitle())
                .withResponseType(Message.ResponseType.IN_CHANNEL)
                .withAttachment(
                        Attachment.builder()
                                .withText(joke.getJoke())
                                .withCallbackId("do_nothing")
                                .withTimestamp(new Date().getTime())
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
}
