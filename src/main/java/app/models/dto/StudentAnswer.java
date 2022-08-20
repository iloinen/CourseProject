package app.models.dto;

public class StudentAnswer {

    private final String question;
    private final String studentAnswer;

    public StudentAnswer(String question, String studentAnswer) {
        this.question = question;
        this.studentAnswer = studentAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

}
