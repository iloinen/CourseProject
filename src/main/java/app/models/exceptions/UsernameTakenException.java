package app.models.exceptions;

public class UsernameTakenException extends Exception {

    public UsernameTakenException() {
        super("This username is taken. Please choose another one!");
    }

}
