package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class Attachment {
    private String fallback;
    private String color;
    private String pretext;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_link")
    private String authorLink;
    @JsonProperty("author_icon")
    private String authorIcon;
    private String title;
    @JsonProperty("title_link")
    private String titleLink;
    private String text;
    private List<Field> fields;
    private List<Action> actions;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("thumb_url")
    private String imageThumb;
    private String footer;
    @JsonProperty("footer_icon")
    private String footerIcon;
    @JsonProperty("ts")
    private long timestamp;


    @JsonIgnore
    public static AttachmentBuilder builder() {
        return new AttachmentBuilder();
    }

    public static class AttachmentBuilder {
        private String fallback;
        private String color;
        private String pretext;
        private String authorName;
        private String authorLink;
        private String authorIcon;
        private String title;
        private String titleLink;
        private String text;
        private List<Field> fields;
        private List<Action> actions;
        private String imageUrl;
        private String imageThumb;
        private String footer;
        private String footerIcon;
        private long timestamp;

        private AttachmentBuilder() {
            this.fields = new ArrayList<>();
            this.actions = new ArrayList<>();
        }

        public AttachmentBuilder withFallback(final String fallback) {
            this.fallback = fallback;
            return this;
        }

        public AttachmentBuilder withColor(final String color) {
            this.color = color;
            return this;
        }

        public AttachmentBuilder withColor(final Color color) {
            this.color = color.getValue();
            return this;
        }

        public AttachmentBuilder withPretext(final String pretext) {
            this.pretext = pretext;
            return this;
        }

        public AttachmentBuilder withAuthorName(final String authorName) {
            this.authorName = authorName;
            return this;
        }

        public AttachmentBuilder withAuthorLink(final String authorLink) {
            this.authorLink = authorLink;
            return this;
        }

        public AttachmentBuilder withAuthorIcon(final String authorIcon) {
            this.authorIcon = authorIcon;
            return this;
        }

        public AttachmentBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public AttachmentBuilder withTitleLink(final String titleLink) {
            this.titleLink = titleLink;
            return this;
        }

        public AttachmentBuilder withFields(final List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public AttachmentBuilder withField(final Field field) {
            this.fields.add(field);
            return this;
        }

        public AttachmentBuilder withActions(final List<Action> actions) {
            this.actions = actions;
            return this;
        }

        public AttachmentBuilder withAction(final Action action) {
            this.actions.add(action);
            return this;
        }

        public AttachmentBuilder withImageUrl(final String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public AttachmentBuilder withImageThumb(final String imageThumb) {
            this.imageThumb = imageThumb;
            return this;
        }

        public AttachmentBuilder withFooter(final String footer) {
            this.footer = footer;
            return this;
        }

        public AttachmentBuilder withFooterIcon(final String footerIcon) {
            this.footerIcon = footerIcon;
            return this;
        }

        public AttachmentBuilder withTimestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AttachmentBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public Attachment build() {
            Attachment attachment = new Attachment();
            attachment.setFallback(fallback);
            attachment.setColor(color);
            attachment.setPretext(pretext);
            attachment.setAuthorName(authorName);
            attachment.setAuthorLink(authorLink);
            attachment.setAuthorIcon(authorIcon);
            attachment.setTitle(title);
            attachment.setTitleLink(titleLink);
            attachment.setFields(fields);
            attachment.setActions(actions);
            attachment.setImageUrl(imageUrl);
            attachment.setImageThumb(imageThumb);
            attachment.setFooter(footer);
            attachment.setFooterIcon(footerIcon);
            attachment.setTimestamp(timestamp);
            attachment.setText(text);
            return attachment;
        }
    }

    public enum Color {
        GREEN("#36a64f");

        @Getter
        private String value;

        Color(String value) {
            this.value = value;
        }
    }
}
