package be.wouterversyck.slackintegration.web.controllers.restControllers;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import be.wouterversyck.slackintegration.web.viewModels.dto.FunFactDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/funfact")
public class FunFactController extends AbstractRestController {
    private FunFactService funFactService;

    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    @GetMapping("/latest")
    public Mono<FunFact> getLatest() {
        return funFactService.getLatest();
    }

    @GetMapping("/random")
    public Mono<FunFact> getRandom() {
        return funFactService.getRandom();
    }

    @GetMapping("/top")
    public Mono<FunFact> getTopVoted() {
        return funFactService.getTopVoted();
    }

    @GetMapping("/{id}")
    public Mono<FunFact> getFunFact(@PathVariable("id") final String id) {
        return funFactService.get(id);
    }

    @GetMapping
    public Flux<FunFact> getAll() {
        return funFactService.getAll();
    }

    @PostMapping
    public Mono<FunFact> addFunFact(@RequestBody @Valid FunFactDto funFact) {
        return funFactService.add(funFact.toFunFactModel());
    }
}
