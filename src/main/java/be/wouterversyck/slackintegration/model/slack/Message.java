package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Message {
    @JsonProperty("response_type")
    private String responseType;
    private String text;
    @JsonProperty("attachments")
    private List<Attachment> slackResponseAttachments;

    @JsonIgnore
    public static MessageBuilder builder() {
        return new MessageBuilder();
    }
    public static class MessageBuilder {
        private String responseType;
        private String text;
        private List<Attachment> attachments;

        private MessageBuilder() {
            this.attachments = new ArrayList<>();
        }

        public MessageBuilder withResponseType(final ResponseType responseType) {
            this.responseType = responseType.getValue();
            return this;
        }

        public MessageBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder withAttachments(final List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public MessageBuilder withAttachment(final Attachment attachment) {
            attachments.add(attachment);
            return this;
        }

        public Message build() {
            Message response = new Message();
            response.setResponseType(responseType);
            response.setText(text);
            response.setSlackResponseAttachments(attachments);
            return response;
        }
    }

    public enum ResponseType {
        IN_CHANNEL("in_channel"),
        EPHEMERAL("ephemeral");

        @Getter
        private String value;

        ResponseType(String value) {
            this.value = value;
        }
    }
}
