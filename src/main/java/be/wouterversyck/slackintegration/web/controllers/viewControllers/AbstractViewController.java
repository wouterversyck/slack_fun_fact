package be.wouterversyck.slackintegration.web.controllers.viewControllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

abstract class AbstractViewController {
    <T> Mono<T> validate(Mono<T> mono, Model model, T returnType) {
        return mono.onErrorResume(e -> {
            if (e instanceof WebExchangeBindException) {
                BindingResult result = ((WebExchangeBindException) e).getBindingResult();
                model.addAttribute("errors", result.getFieldErrors().stream()
                        .collect(
                                Collectors.toMap(
                                    FieldError::getField,
                                    this::getFieldValue
                                )
                        )
                );
            }
            return Mono.just(returnType);
        });
    }

    private String getFieldValue(FieldError fieldError) {
        String value = fieldError.getDefaultMessage();
        return value == null || value.isEmpty() ? "empty" : value;
    }
}
