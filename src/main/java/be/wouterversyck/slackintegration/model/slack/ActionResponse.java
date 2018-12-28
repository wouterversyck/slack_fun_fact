package be.wouterversyck.slackintegration.model.slack;

import be.wouterversyck.slackintegration.model.common.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ActionResponse {

    private String type;
    private List<Action> actions;
    @JsonProperty("callback_id")
    private String callbackId;
    @JsonProperty("action_ts")
    private String actionTimeStamp;
    @JsonProperty("message_ts")
    private String messageTimeStamp;
    @JsonProperty("attachment_id")
    private long attachmentId;
    private String token;
    @JsonProperty("original_message")
    private Message originalMessage;
    @JsonProperty("response_url")
    private String responseUrl;
    @JsonProperty("trigger_id")
    private String triggerId;
    private Team team;
    private User user;
    private Channel channel;
}
