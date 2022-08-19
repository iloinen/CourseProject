package app.models.quiz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TaskAnswer {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private AnswerBase answer;

    private boolean isCorrect;

    public TaskAnswer() {}

    public TaskAnswer(Task task, AnswerBase answer, boolean isCorrect) {
        this.task = task;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public TaskAnswer(long id, Task task, AnswerBase answer, boolean isCorrect) {
        this(task, answer, isCorrect);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public AnswerBase getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBase answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

}
