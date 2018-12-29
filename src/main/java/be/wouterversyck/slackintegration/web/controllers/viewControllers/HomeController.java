package be.wouterversyck.slackintegration.web.controllers.viewControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"", "/index"})
    public String index(Model model, Authentication principal) {
        model.addAttribute("principal", principal);
        return "index";
    }
}
