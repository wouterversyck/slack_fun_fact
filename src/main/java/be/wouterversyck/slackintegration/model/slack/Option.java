package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Option {
    private String text;
    private String value;

    @JsonIgnore
    public static OptionBuilder builder() {
        return new OptionBuilder();
    }

    public static class OptionBuilder {
        private String text;
        private String value;

        public OptionBuilder withText(final String text) {
            this.text = text;
            return this;
        }

        public OptionBuilder withValue(final String value) {
            this.value = value;
            return this;
        }

        public Option build() {
            Option option = new Option();
            option.setText(text);
            option.setValue(value);

            return option;
        }
    }
}
