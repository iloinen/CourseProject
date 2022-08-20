package app.models.dto;

import java.util.List;

public class TaskDto {

    private final String question;
    private final List<AnswerDto> answers;

    public TaskDto(String question, List<AnswerDto> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

}
