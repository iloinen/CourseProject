package app.models.quiz;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TaskCategory {

    @Id
    private String categoryName;

    public TaskCategory() {}

    public TaskCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
