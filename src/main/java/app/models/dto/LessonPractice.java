package app.models.dto;

import java.util.List;

public class LessonPractice {

    private final long lessonId;
    private final String lessonTitle;
    private final List<TaskDto> tasks;

    private int taskIndex;

    public LessonPractice(long lessonId, String lessonTitle, List<TaskDto> tasks) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.tasks = tasks;
    }

    public void increaseIndex() {
        taskIndex++;
    }

    public TaskDto next() {
        return tasks.get(taskIndex);
    }

    public long getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

}
