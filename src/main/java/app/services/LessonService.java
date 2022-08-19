package app.services;

import app.models.quiz.Lesson;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class LessonService {

    @PersistenceContext
    private EntityManager em;

    public List<Lesson> findAll() {
        return em
                .createQuery("SELECT l FROM Lesson l", Lesson.class)
                .getResultList();
    }

}
