package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Confirm {
    private String title;
    private String text;
    @JsonProperty("ok_text")
    private String okText;
    @JsonProperty("dismiss_text")
    private String dismissText;

    @JsonIgnore
    public ConfirmBuilder builder() {
        return new ConfirmBuilder();
    }

    public static class ConfirmBuilder {
        private String title;
        private String text;
        private String okText;
        private String dismissText;

        public ConfirmBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public ConfirmBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public ConfirmBuilder withOkText(final String okText) {
            this.okText = okText;
            return this;
        }

        public ConfirmBuilder withDismissText(final String dismissText) {
            this.dismissText = dismissText;
            return this;
        }
    }
}
