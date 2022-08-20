package app.services;

import app.models.dto.AnswerDto;
import app.models.dto.LessonPractice;
import app.models.dto.TaskDto;
import app.models.quiz.TaskAnswer;
import app.repos.TaskAnswerRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskAnswerRepo repo;

    public TaskService(TaskAnswerRepo repo) {
        this.repo = repo;
    }

    public boolean isCorrect(long taskAnswerId) {
        Optional<TaskAnswer> taskAnswer = repo.findById(taskAnswerId);

        if (taskAnswer.isPresent()) {
            return taskAnswer.get().isCorrect();
        }

        throw new RuntimeException("No task found.");
    }

    public boolean isCorrect(TaskDto task, String studentAnswer) {
        for (AnswerDto answer : task.getAnswers()) {
            if (answer.getText().equals(studentAnswer) && answer.isCorrect()) {
                return true;
            }
        }

        return false;
    }

}
