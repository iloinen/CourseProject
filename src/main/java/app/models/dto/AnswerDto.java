package app.models.dto;

public class AnswerDto {

    private final String text;
    private final boolean isCorrect;

    public AnswerDto(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
