package be.wouterversyck.slackintegration.model.slack;

import be.wouterversyck.slackintegration.model.FunFact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SlackResponse {
    @JsonProperty("response_type")
    private String responseType;
    private String text;
    @JsonProperty("attachments")
    private List<SlackResponseAttachment> slackResponseAttachments;

    @JsonIgnore
    public static SlackResponse fromFunFact(final FunFact funFact) {
        return builder()
                .withText(funFact.getTitle())
                .withResponseType(ResponseType.IN_CHANNEL)
                .withAttachment(
                        SlackResponseAttachment.builder()
                                .withText(funFact.getFunFact())
                                .build()
                ).build();
    }

    @JsonIgnore
    public static SlackResponseBuilder builder() {
        return new SlackResponseBuilder();
    }
    public static class SlackResponseBuilder {
        private String responseType;
        private String text;
        private List<SlackResponseAttachment> attachments;

        private SlackResponseBuilder() {
            this.attachments = new ArrayList<>();
        }

        public SlackResponseBuilder withResponseType(final String responseType) {
            this.responseType = responseType;
            return this;
        }

        public SlackResponseBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public SlackResponseBuilder withAttachments(final List<SlackResponseAttachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public SlackResponseBuilder withAttachment(final SlackResponseAttachment attachment) {
            attachments.add(attachment);
            return this;
        }

        public SlackResponse build() {
            SlackResponse response = new SlackResponse();
            response.setResponseType(responseType);
            response.setText(text);
            response.setSlackResponseAttachments(attachments);
            return response;
        }
    }

    public static final class ResponseType {
        public static final String IN_CHANNEL = "in_channel";
    }
}
