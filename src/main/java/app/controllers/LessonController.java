package app.controllers;

import app.models.dto.LessonPractice;
import app.models.exceptions.LessonNotFoundException;
import app.services.LessonService;
import app.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LessonController {

    private final LessonService lessonService;
    private final TaskService taskService;

    private LessonPractice practice;

    public LessonController(LessonService lessonService, TaskService taskService) {
        this.lessonService = lessonService;
        this.taskService = taskService;
    }

    @GetMapping(value = {"/lessons"})
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }

    @GetMapping(value = {"/lessons/{id}"})
    public String getOneLesson(@PathVariable(name = "id") long lessonId, Model model) {
        String errorMsg = null;

        try {
            if (practice == null || practice.getLessonId() != lessonId) {
                practice = lessonService.practiceByLessonId(lessonId);
            }
        } catch (LessonNotFoundException e) {
            errorMsg = e.getMessage();
        }

        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("practice", practice);

        return "lessons_one";
    }

    @PostMapping(value = {"/lessons/tasks"})
    public String checkUserAnswer(
            @RequestParam(name = "studentAnswer") String studentAnswer,
            Model model) {
        String errorMsg = null;
        boolean isCorrect = false;

        try {
            isCorrect = taskService.isCorrect(practice.next(), studentAnswer);
            practice.increaseIndex();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }

        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("isCorrect", isCorrect);

        return "redirect:/lessons/" + practice.getLessonId();
    }

}
