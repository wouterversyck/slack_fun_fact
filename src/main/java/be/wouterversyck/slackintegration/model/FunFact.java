package be.wouterversyck.slackintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Document(collection = "fun_facts")
public class FunFact {
    @Id
    private String id;
    private String author;
    @NotEmpty(message = "fun_fact cannot be empty")
    @JsonProperty("fun_fact")
    @Field("fun_fact")
    private String funFact;
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @CreatedDate
    @Field("create_date")
    @JsonProperty("create_date")
    private Date createDate;
}
