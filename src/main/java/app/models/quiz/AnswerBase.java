package app.models.quiz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AnswerBase {

    @Id
    @GeneratedValue
    private long id;

    private String text;

    public AnswerBase() {}

    public AnswerBase(String text) {
        this.text = text;
    }

    public AnswerBase(long id, String text) {
        this(text);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
