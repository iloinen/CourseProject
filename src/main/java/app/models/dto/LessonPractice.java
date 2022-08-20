package app.models.dto;

import java.util.ArrayList;
import java.util.List;

public class LessonPractice {

    private final long lessonId;
    private final String lessonTitle;
    private final List<TaskDto> tasks;

    private List<StudentAnswer> studentMistakes;

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

    public List<StudentAnswer> getStudentMistakes() {
        return studentMistakes;
    }

    public void addCurrentToMistakes(String studentAnswer) {
        if (studentMistakes == null) {
            studentMistakes = new ArrayList<>();
        }

        studentMistakes.add(new StudentAnswer(next().getQuestion(), studentAnswer));
    }

    public boolean moreTasks() {
        return taskIndex < tasks.size();
    }
}
