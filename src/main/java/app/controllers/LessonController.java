package app.controllers;

import app.models.dto.LessonPractice;
import app.models.dto.TaskDto;
import app.models.exceptions.LessonNotFoundException;
import app.services.LessonService;
import app.services.PracticeService;
import app.services.TaskService;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LessonController {

    private final LessonService lessonService;
    private final TaskService taskService;
    private final PracticeService practiceService;

    public LessonController(LessonService lessonService, TaskService taskService, PracticeService practiceService) {
        this.lessonService = lessonService;
        this.taskService = taskService;
        this.practiceService = practiceService;
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
            practiceService.setPractice(lessonId);
        } catch (LessonNotFoundException e) {
            errorMsg = e.getMessage();
        }

        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("practice", practiceService.getPractice());

        return "lessons_one";
    }

    @PostMapping(value = {"/lessons/tasks"})
    public String checkUserAnswer(
            @RequestParam(name = "studentAnswer") String studentAnswer,
            Model model) {
        practiceService.checkAndSetNext(studentAnswer);

        if (practiceService.moreTasks()) {
            return "redirect:/lessons/" + practiceService.getPractice().getLessonId();
        }

        model.addAttribute("mistakes", practiceService.getPractice().getStudentMistakes());

        return "finishedLesson";
    }

}
