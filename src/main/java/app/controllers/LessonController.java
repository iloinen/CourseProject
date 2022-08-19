package app.controllers;

import app.models.exceptions.LessonNotFoundException;
import app.models.quiz.Lesson;
import app.services.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping(value = {"/lessons"})
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }

    @GetMapping(value = {"/lessons/{id}"})
    public String getOneLesson(@PathVariable(name = "id") long lessonId, Model model) {
        String errorMsg = null;
        Lesson lessonById = null;

        try {
            lessonById = lessonService.findById(lessonId);
        } catch (LessonNotFoundException e) {
            errorMsg = e.getMessage();
        }

        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("lesson", lessonById);

        return "lessons_one";
    }

}
