package be.wouterversyck.slackintegration.model.funFact;

import be.wouterversyck.slackintegration.model.common.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Document(collection = "fun_facts")
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
    private List<Vote> votes;

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
    public List<Vote> getVotes() {
        if(this.votes == null) {
            this.votes = new ArrayList<>();
        }

        return this.votes;
    }
}
