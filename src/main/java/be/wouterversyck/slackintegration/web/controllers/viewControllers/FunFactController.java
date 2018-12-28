package be.wouterversyck.slackintegration.web.controllers.viewControllers;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.services.databaseServices.FunFactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.lang.String.format;

@Controller("funFactViewController")
@RequestMapping("view/funfact")
public class FunFactController {

    private FunFactService funFactService;

    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    @GetMapping("")
    public String getForm(Model model) {
        model.addAttribute("funFact", new FunFact());
        return "funFactForm";
    }

    @GetMapping("{id}")
    public Mono<String> getFact(@PathVariable("id") String id, Model model) {
        return funFactService.get(id)
                .doOnNext(e -> model.addAttribute("fact", e))
                .map(e -> "funFact");
    }

    @PostMapping("")
    public Mono<String> addFunFact(Model model, @Valid FunFact funFact, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            // TODO after bean validation fix, do something here
        }

        return funFactService.save(funFact)
            .map(e -> format("redirect:/view/funfact/%s", e.getId()));
    }
}
