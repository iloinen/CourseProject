package app.services;

import app.models.dto.AnswerDto;
import app.models.dto.LessonPractice;
import app.models.exceptions.LessonNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PracticeService {

    private LessonPractice practice;

    private final LessonService lessonService;

    public PracticeService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    public LessonPractice getPractice() {
        return practice;
    }

    public void setPractice(long lessonId) throws LessonNotFoundException {
        if (practice == null || practice.getLessonId() != lessonId || !practice.moreTasks()) {
            practice = lessonService.practiceByLessonId(lessonId);
        }
    }

    public void checkAndSetNext(String studentAnswer) {
        boolean isCorrect = isCorrect(studentAnswer);

        if (!isCorrect) {
            practice.addCurrentToMistakes(studentAnswer);
        }

        practice.increaseIndex();
    }

    public boolean moreTasks() {
        return practice.moreTasks();
    }

    private boolean isCorrect(String studentAnswer) {
        for (AnswerDto answer : practice.next().getAnswers()) {
            if (answer.isCorrect() && answer.getText().equals(studentAnswer)) {
                return true;
            }
        }

        return false;
    }

}
