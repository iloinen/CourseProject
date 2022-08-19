package app.controllers;

import app.services.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping(value = {"/lessons"})
    public String getAllLessons(Model model) {
        // TODO
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }

}
