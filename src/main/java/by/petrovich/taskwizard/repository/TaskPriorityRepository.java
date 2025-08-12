package by.petrovich.taskwizard.repository;

import by.petrovich.taskwizard.model.TaskPriority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {
    List<TaskPriority> findAll(Sort sort);

    Optional<TaskPriority> findById(Long aLong);

    List<TaskPriority> findByNameIgnoreCase(String name);

}
