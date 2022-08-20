package app.repos;

import app.models.quiz.TaskAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAnswerRepo extends JpaRepository<TaskAnswer, Long> {
}
