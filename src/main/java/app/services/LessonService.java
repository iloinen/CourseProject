package app.services;

import app.models.dto.AnswerDto;
import app.models.dto.LessonPractice;
import app.models.dto.TaskDto;
import app.models.exceptions.LessonNotFoundException;
import app.models.quiz.Lesson;
import app.models.quiz.Task;
import app.models.quiz.TaskAnswer;
import app.repos.LessonRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepo lessonRepo;

    public LessonService(LessonRepo lessonRepo) {
        this.lessonRepo = lessonRepo;
    }

    public List<Lesson> findAll() {
        return lessonRepo.findAll();
    }

    public Lesson findById(long id) throws LessonNotFoundException {
        Optional<Lesson> lesson = lessonRepo.findById(id);

        if (lesson.isPresent()) {
            return lesson.get();
        }

        throw new LessonNotFoundException();
    }

    public LessonPractice practiceByLessonId(long lessonId) throws LessonNotFoundException {
        Lesson lesson = findById(lessonId);

        return createPractice(lesson);
    }

    private LessonPractice createPractice(Lesson lesson) {
        List<TaskDto> tasksForLesson = createTasks(lesson);
        return new LessonPractice(lesson.getId(), lesson.getTitle(), tasksForLesson);
    }

    private List<TaskDto> createTasks(Lesson lesson) {
        List<TaskDto> tasks = new ArrayList<>();

        for (Task task : lesson.getTasks()) {
            List<AnswerDto> answers = createAnswers(task);
            tasks.add(new TaskDto(task.getQuestion(), answers));
        }

        return tasks;
    }

    private List<AnswerDto> createAnswers(Task task) {
        List<AnswerDto> answers = new ArrayList<>();

        for (TaskAnswer answer : task.getAnswers()) {
            answers.add(new AnswerDto(answer.getAnswer().getText(), answer.isCorrect()));
        }

        return answers;
    }

}
