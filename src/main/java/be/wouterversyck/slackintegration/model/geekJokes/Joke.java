package be.wouterversyck.slackintegration.model.geekJokes;

import lombok.Data;
import lombok.NonNull;

@Data
public class Joke {
    private String joke;
    private String title;

    public Joke(@NonNull final String joke, @NonNull final String title) {
        this.joke = joke;
        this.title = title;
    }

    public Joke(@NonNull final String joke) {
        this.joke = joke;
    }
}
