package by.petrovich.taskwizard.repository;

import by.petrovich.taskwizard.model.TaskStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    List<TaskStatus> findAll(Sort sort);

    Optional<TaskStatus> findById(Long aLong);

    List<TaskStatus> findByNameIgnoreCase(String name);
}
