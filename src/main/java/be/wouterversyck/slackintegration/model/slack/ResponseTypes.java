package be.wouterversyck.slackintegration.model.slack;

public enum ResponseTypes {
    IN_CHANNEL("in_channel");

    private String responseType;
    ResponseTypes(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }
}
