package app.configs;

import app.models.quiz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSaver implements ApplicationRunner {

    @PersistenceContext
    private EntityManager em;

    private final DataHolder holder;

    private List<Lesson> lessons;
    private final List<TaskCategory> categories;

    private List<AnswerBase> answerBases;


    private List<Task> tasksForJava;
    private List<Task> tasksForOOP;
    private List<Task> tasksForSpring;

    @Autowired
    public DataSaver(
            DataHolder holder,
            List<Lesson> lessons, List<TaskCategory> categories) {
        this.holder = holder;
        this.lessons = lessons;
        this.categories = categories;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (shouldSave()) {
            saveLessons();
            saveCategories();
            saveAnswerBases();

            saveTasks();
            saveTaskAnswers();
        }
    }

    @Transactional
    public boolean shouldSave() {
        return em.createQuery("SELECT l FROM Lesson l", Lesson.class).getResultList().size() == 0;
    }

    @Transactional
    public void saveLessons() {
        for (Lesson lesson : lessons) {
            em.persist(lesson);
        }

        em.flush();

        lessons = em.createQuery("SELECT l FROM Lesson l", Lesson.class).getResultList();
    }

    @Transactional
    public void saveCategories() {
        for (TaskCategory category : categories) {
            em.persist(category);
        }
    }

    @Transactional
    public void saveAnswerBases() {
        saveAnswerBases(holder.createAnswerBasesForJava());
        saveAnswerBases(holder.createAnswerBasesForOOP());
        saveAnswerBases(holder.createAnswerBasesForSpring());

        answerBases = em.createQuery("SELECT a FROM AnswerBase a", AnswerBase.class).getResultList();
    }

    @Transactional
    public void saveAnswerBases(List<AnswerBase> answerBases) {
        for (AnswerBase answerBase : answerBases) {
            em.persist(answerBase);
        }
    }

    @Transactional
    public void saveTasks() {
        List<Task> tasksForJava = holder.createTasks(lessons.get(0), "Melyik nem primitív adattípus?", "Hogy vagy?", 0);
        List<Task> tasksForOOP = holder.createTasks(lessons.get(1), "Melyik nem láthatósági jelző?", "Hogy vagy?", 1);
        List<Task> tasksForSpring = holder.createTasks(lessons.get(2), "Melyik nem tesz bean-né?", "Hogy vagy?", 2);

        saveTasks(lessons.get(0), tasksForJava);
        saveTasks(lessons.get(1), tasksForOOP);
        saveTasks(lessons.get(2), tasksForSpring);

        this.tasksForJava = tasksFromDb(lessons.get(0));
        this.tasksForOOP = tasksFromDb(lessons.get(1));
        this.tasksForSpring = tasksFromDb(lessons.get(2));
    }

    @Transactional
    public void saveTasks(Lesson lesson, List<Task> tasks) {
        for (Task task : tasks) {
            em.persist(task);
        }

        lesson.setTasks(tasks);
    }

    @Transactional
    public List<Task> tasksFromDb(Lesson lesson) {
        return em.createQuery(
                "SELECT t FROM Task t WHERE t.lesson.title = :lessonName",
                Task.class)
                .setParameter("lessonName", lesson.getTitle())
                .getResultList();
    }

    @Transactional
    public void saveTaskAnswers() {
        saveTaskAnswers(tasksForJava.get(0), 1, "boolean", "String", "int", "char", "byte");
        saveTaskAnswers(tasksForOOP.get(0), 4, "public", "protected", "private", "package private", "static");
        saveTaskAnswers(tasksForSpring.get(0), 0, "@Autowired", "@Bean", "@Component", "@Service");
    }

    @Transactional
    public void saveTaskAnswers(Task task, int correctId, String... answerTexts) {
        List<AnswerBase> bases = new ArrayList<>();

        for (String text : answerTexts) {
            bases.add(byText(text));
        }

        List<TaskAnswer> answers = holder.createTaskAnswers(task, correctId, bases);

        for (TaskAnswer answer : answers) {
            em.persist(answer);
        }

        task.setAnswers(answers);
    }

    private AnswerBase byText(String text) {
        for (AnswerBase base : answerBases) {
            if (base.getText().equals(text)) {
                return base;
            }
        }

        return null;
    }

}
