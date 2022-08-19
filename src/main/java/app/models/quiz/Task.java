package app.models.quiz;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    private String question;

    @ManyToOne
    private Lesson lesson;

    @ManyToMany
    private List<TaskCategory> categories;

    @OneToMany(mappedBy = "task")
    private List<TaskAnswer> answers;

    public Task() {}

    public Task(String question, Lesson lesson) {
        this.question = question;
        this.lesson = lesson;
    }

    public Task(long id, String question, Lesson lesson, List<TaskCategory> categories, List<TaskAnswer> answers) {
        this(question, lesson);
        this.id = id;
        this.categories = categories;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<TaskCategory> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }

        return categories;
    }

    public void setCategories(List<TaskCategory> categories) {
        this.categories = categories;
    }

    public List<TaskAnswer> getAnswers() {
        if (answers == null) {
            answers = new ArrayList<>();
        }

        return answers;
    }

    public void setAnswers(List<TaskAnswer> answers) {
        this.answers = answers;
    }

}
