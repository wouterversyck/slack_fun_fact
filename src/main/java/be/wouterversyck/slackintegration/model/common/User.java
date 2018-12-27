package be.wouterversyck.slackintegration.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {
    private String id;
    private String name;

    @JsonIgnore
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String id;
        private String name;

        private UserBuilder() {}

        public UserBuilder withId(final String id) {
            this.id = id;
            return this;
        }

        public UserBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setName(name);

            return user;
        }
    }
}
