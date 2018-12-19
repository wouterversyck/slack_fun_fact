package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SlackResponseAttachment {
    private String text;

    @JsonIgnore
    public static SlackResponseAttachmentBuilder builder() {
        return new SlackResponseAttachmentBuilder();
    }

    public static class SlackResponseAttachmentBuilder {
        private String text;

        private SlackResponseAttachmentBuilder() {}

        public SlackResponseAttachmentBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public SlackResponseAttachment build() {
            SlackResponseAttachment attachment = new SlackResponseAttachment();
            attachment.setText(text);
            return attachment;
        }
    }
}
