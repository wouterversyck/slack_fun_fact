package be.wouterversyck.slackintegration.web.controllers;

import be.wouterversyck.slackintegration.model.FunFact;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/funfact")
public class FunFactController extends AbstractController<FunFact> {
    private FunFactService funFactService;

    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }
    @GetMapping("/latest")
    public Mono<ResponseEntity<FunFact>> getLatest() {
        return onEmptyNotFound(funFactService.getLatest());
    }

    @GetMapping("/random")
    public Mono<ResponseEntity<FunFact>> getRandom() {
        return onEmptyNotFound(funFactService.getRandom());
    }

    @GetMapping("/top")
    public Mono<ResponseEntity<FunFact>> getTopVoted() {
        return onEmptyNotFound(funFactService.getTopVoted());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FunFact>> getFunFact(@PathVariable("id") final String id) {
        return onEmptyNotFound(funFactService.get(id));
    }

    @GetMapping
    public Flux<FunFact> getAll() {
        return funFactService.getAll();
    }

    @PostMapping
    public Mono<FunFact> addFunFact(@Valid @RequestBody FunFact funFact) {
        return funFactService.add(funFact);
    }
}
