package app.configs;

import app.models.quiz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataHolder {

    @Bean
    private List<Lesson> createLessons() {
        return Arrays.asList(
                new Lesson("A Java alapjai"),
                new Lesson("Az OOP alapjai"),
                new Lesson("A Spring alapjai")
        );
    }

    @Bean
    private List<TaskCategory> createCategories() {
        return Arrays.asList(
                new TaskCategory("Java alapok"),
                new TaskCategory("Java OOP"),
                new TaskCategory("Spring keretrendszer")
        );
    }

    public List<AnswerBase> createAnswerBasesForJava() {
        return Arrays.asList(
                new AnswerBase("boolean"),
                new AnswerBase("String"),
                new AnswerBase("int"),
                new AnswerBase("char"),
                new AnswerBase("byte")
        );
    }

    public List<AnswerBase> createAnswerBasesForOOP() {
        return Arrays.asList(
                new AnswerBase("public"),
                new AnswerBase("protected"),
                new AnswerBase("private"),
                new AnswerBase("package private"),
                new AnswerBase("static")
        );
    }

    public List<AnswerBase> createAnswerBasesForSpring() {
        return Arrays.asList(
                new AnswerBase("@Autowired"),
                new AnswerBase("@Bean"),
                new AnswerBase("@Component"),
                new AnswerBase("@Service")
        );
    }

    public List<Task> createTasks(Lesson lesson, String q1, String q2, int catId) {
        return Arrays.asList(
                createTask(q1, lesson, createCategories().get(catId)),
                createTask(q2, lesson, createCategories().get(catId))
        );
    }

    private Task createTask(String question, Lesson lesson, TaskCategory... categories) {
        Task task = new Task(question, lesson);

        task.setCategories(Arrays.asList(categories));

        return task;
    }

    public List<TaskAnswer> createTaskAnswers(Task task, int correctId, List<AnswerBase> answers) {
        List<TaskAnswer> taskAnswers = new ArrayList<>();

        for (int i = 0; i < answers.size(); i++) {
            taskAnswers.add(new TaskAnswer(task, answers.get(i), i == correctId));
        }

        return taskAnswers;
    }

}
