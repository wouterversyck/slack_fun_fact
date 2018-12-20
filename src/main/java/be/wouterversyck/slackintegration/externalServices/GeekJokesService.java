package be.wouterversyck.slackintegration.externalServices;

import be.wouterversyck.slackintegration.model.geekJokes.Joke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeekJokesService {

    @Value("${geek.jokes.api}")
    private String GEEK_JOKE_API;
    private WebClient client = WebClient.create();

    public Mono<Joke> getJoke() {
        return client.get()
                .uri(GEEK_JOKE_API)
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .flatMap(res -> res.bodyToMono(String.class))
                .map(Joke::new);
    }
}
