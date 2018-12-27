package be.wouterversyck.slackintegration.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {
    private String id;
    private String name;
}
