package be.wouterversyck.slackintegration.model.slack;

import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.model.geekJokes.Joke;

import java.util.Date;

public class MessageConverter {

    public static Message fromFunFact(final FunFact funFact) {
        return Message.builder()
                .withText(funFact.getTitle())
                .withResponseType(Message.ResponseType.IN_CHANNEL)
                .withAttachment(
                        Attachment.builder()
                                .withText(funFact.getFunFact())
                                .withAuthorName(funFact.getAuthor())
                                .withTimestamp(funFact.getCreateDate().getTime())
                                .withColor(Attachment.Color.GREEN)
                                .withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Was this dank?")
                                                .withValue("Nice?")
                                                .build()
                                ).withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Not so dank")
                                                .withValue("Not Nice!")
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
                                .withTimestamp(new Date().getTime())
                                .withColor(Attachment.Color.GREEN)
                                .withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Was this dank?")
                                                .withValue("Nice?")
                                                .build()
                                ).withAction(
                                        Action.builder()
                                                .withName("appraisal")
                                                .withType(Action.ActionType.BUTTON)
                                                .withText("Not so dank")
                                                .withValue("Not Nice!")
                                                .build()
                        ).build()
                ).build();
    }
}
