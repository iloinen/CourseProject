package app.controllers;

import app.services.TextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TextService textService;

    public HomeController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        model.addAttribute("first", textService.loremStart());
        model.addAttribute("second", textService.paragraph(true));
        model.addAttribute("third", textService.paragraph(false));

        return "index";
    }

}
