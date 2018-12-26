package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Field {
    private String title;
    private String value;
    @JsonProperty("short")
    private boolean isShort;

    @JsonIgnore
    public static FieldBuilder builder() {
        return new FieldBuilder();
    }

    public static class FieldBuilder {
        private String title;
        private String value;
        private boolean isShort;

        public FieldBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public FieldBuilder withValue(final String value) {
            this.value = value;
            return this;
        }

        public FieldBuilder withTitle(final boolean isShort) {
            this.isShort = isShort;
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.setShort(isShort);
            field.setValue(value);
            field.setTitle(title);

            return field;
        }
    }
}
