package be.wouterversyck.slackintegration.web.controllers.viewControllers;

import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import be.wouterversyck.slackintegration.web.viewModels.dto.FunFactDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.lang.String.format;

@Validated
@Controller("funFactViewController")
@RequestMapping("view/funfact")
public class FunFactController extends AbstractViewController{

    private FunFactService funFactService;

    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    @GetMapping("")
    public String getForm(Model model) {
        model.addAttribute("funFact", new FunFactDto());
        return "funFactForm";
    }

    @GetMapping("{id}")
    public Mono<String> getFact(@PathVariable("id") String id, Model model) {
        return funFactService.get(id)
                .doOnNext(e -> model.addAttribute("fact", e))
                .map(e -> "funFact");
    }

    @PostMapping("")
    public Mono<String> addFunFact(@Valid Mono<FunFactDto> fact, Model model) {
        return validate(
                fact.map(FunFactDto::toFunFactModel)
                    .flatMap(funFactService::save)
                    .map(e -> format("redirect:/view/funfact/%s", e.getId())),
                model,
                "funFactForm");
    }
}
