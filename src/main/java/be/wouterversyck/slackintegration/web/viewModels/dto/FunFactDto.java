package be.wouterversyck.slackintegration.web.viewModels.dto;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FunFactDto {

    @NotBlank(message = "fun_fact cannot be empty")
    @JsonProperty("fun_fact")
    private String funFact;

    @NotBlank(message = "title cannot be empty")
    private String title;

    private String author;

    @JsonIgnore
    public FunFact toFunFactModel() {
        FunFact fact = new FunFact();
        fact.setAuthor(author);
        fact.setFunFact(funFact);
        fact.setTitle(title);

        return fact;
    }
}
