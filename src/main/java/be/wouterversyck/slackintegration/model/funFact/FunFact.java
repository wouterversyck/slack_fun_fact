package be.wouterversyck.slackintegration.model.funFact;

import be.wouterversyck.slackintegration.model.common.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Data
@Document(collection = "fun_facts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunFact {

    @Id
    private String id;
    private String author;
    @NotBlank(message = "fun_fact cannot be empty")
    @JsonProperty("fun_fact")
    @Field("fun_fact")
    private String funFact;
    @NotBlank(message = "title cannot be empty")
    private String title;
    @CreatedDate
    @Field("create_date")
    @JsonProperty("create_date")
    private Date createDate;
    @JsonIgnore
    private Set<Vote> votes;

    public FunFact() {
        this.votes = new HashSet<>();
        this.createDate = new Date();
    }

    @JsonProperty("create_date_unix")
    public long getCreateDateUnix() {
        return createDate.getTime() / 1000;
    }

    public int getVoteCount() {
        if(this.getVotes() == null) { return 0; }

        int votes = 0;
        for(Vote vote : this.getVotes()) {
            if(vote.isUpVote()) {
                ++votes;
            } else {
                --votes;
            }
        }

        return votes;
    }

    @JsonIgnore
    public Optional<Vote> userHasVoted(User user) {
        if(votes == null) { return Optional.empty(); }

        return votes.stream()
                .filter(e -> e.getUser().equals(user))
                .findFirst();
    }

    @JsonIgnore
    public Set<Vote> getVotes() {
        return this.votes;
    }

    @JsonIgnore
    public static FunFactBuilder builder() {
        return new FunFactBuilder();
    }

    public static class FunFactBuilder {
        private String author;
        private String funFact;
        private String title;

        private FunFactBuilder() {}

        public FunFactBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public FunFactBuilder withFunfact(String funFact) {
            this.funFact = funFact;
            return this;
        }

        public FunFactBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public FunFact build() {
            FunFact funFact = new FunFact();
            funFact.setAuthor(author);
            funFact.setTitle(title);
            funFact.setFunFact(this.funFact);

            return funFact;
        }
    }
}
