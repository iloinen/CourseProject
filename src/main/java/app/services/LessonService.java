package app.services;

import app.models.exceptions.LessonNotFoundException;
import app.models.quiz.Lesson;
import app.repos.LessonRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepo lessonRepo;

    public LessonService(LessonRepo lessonRepo) {
        this.lessonRepo = lessonRepo;
    }

    public List<Lesson> findAll() {
        return lessonRepo.findAll();
    }

    public Lesson findById(long id) throws LessonNotFoundException {
        Optional<Lesson> lesson = lessonRepo.findById(id);

        if (lesson.isPresent()) {
            return lesson.get();
        }

        throw new LessonNotFoundException();
    }

}
