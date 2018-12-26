package be.wouterversyck.slackintegration.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class Action {
    private String name;
    private String type;
    private String text;
    private String style;
    private String value;
    @JsonProperty("data_source")
    private String dataSource;
    private List<Option> options;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Confirm confirm;

    @JsonIgnore
    public static ActionBuilder builder() {
        return new ActionBuilder();
    }

    public static class ActionBuilder {
        private String name;
        private String type;
        private String text;
        private String style;
        private String value;
        @JsonProperty("data_source")
        private String dataSource;
        private List<Option> options;
        private Confirm confirm;

        public ActionBuilder() {
            this.options = new ArrayList<>();
        }

        public ActionBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public ActionBuilder withType(final ActionType actionType) {
            type = actionType.getValue();
            return this;
        }

        public ActionBuilder withText(final String text) {
            this.text = text;
            return this;
        }
        public ActionBuilder withStyle(final String style) {
            this.style = style;
            return this;
        }
        public ActionBuilder withValue(final String value) {
            this.value = value;
            return this;
        }
        public ActionBuilder withDataSource(final String dataSource) {
            this.dataSource = dataSource;
            return this;
        }
        public ActionBuilder withOptions(final List<Option> options) {
            this.options = options;
            return this;
        }
        public ActionBuilder withOption(final Option option) {
            this.options.add(option);
            return this;
        }
        public ActionBuilder withConfirm(final Confirm confirm) {
            this.confirm = confirm;
            return this;
        }

        public Action build() {
            Action action = new Action();
            action.setName(name);
            action.setType(type);
            action.setText(text);
            action.setStyle(style);
            action.setValue(value);
            action.setDataSource(dataSource);
            action.setOptions(options);
            action.setConfirm(confirm);

            return action;
        }
    }

    public enum ActionType {
        BUTTON("button");

        @Getter
        private String value;

        ActionType(final String value) {
            this.value = value;
        }
    }
}
