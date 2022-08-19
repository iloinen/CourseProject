package app.models.quiz;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lesson {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(length = 400)
    private String description;

    @OneToMany(mappedBy = "lesson")
    private List<Task> tasks;

    public Lesson() {}

    public Lesson(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Lesson(long id, String title, String description, List<Task> tasks) {
        this(title, description);
        this.id = id;
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String shortDescription) {
        this.description = shortDescription;
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
