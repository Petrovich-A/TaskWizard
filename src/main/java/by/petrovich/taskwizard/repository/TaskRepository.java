package by.petrovich.taskwizard.repository;

import by.petrovich.taskwizard.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByDescriptionContainingIgnoreCase(String description);

}
