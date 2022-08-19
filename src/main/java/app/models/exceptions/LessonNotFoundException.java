package app.models.exceptions;

public class LessonNotFoundException extends Exception {

    public LessonNotFoundException() {
        super("This lesson does not exist.");
    }

}
