package be.wouterversyck.slackintegration.model.funFact;

import be.wouterversyck.slackintegration.model.common.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Vote {
    private boolean upVote;
    private User user;

    @JsonIgnore
    public static VoteBuilder builder() {
        return new VoteBuilder();
    }

    public static class VoteBuilder {
        private boolean upVote;
        private User user;

        private VoteBuilder() {}

        public VoteBuilder withUpvote() {
            this.upVote = true;
            return this;
        }

        public VoteBuilder withDownVote() {
            this.upVote = false;
            return this;
        }

        public VoteBuilder withVote(final boolean upVote) {
            this.upVote = upVote;
            return this;
        }

        public VoteBuilder withUser(final User user) {
            this.user = user;
            return this;
        }

        public Vote build() {
            Vote vote = new Vote();
            vote.setUpVote(upVote);
            vote.setUser(user);

            return vote;
        }
    }
}
