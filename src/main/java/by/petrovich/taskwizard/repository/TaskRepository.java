package by.petrovich.taskwizard.repository;

import by.petrovich.taskwizard.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll(Sort sort);

    Page<Task> findByAuthor_Id(Long id, Pageable pageable);

    Page<Task> findByAssignee_Id(Long id, Pageable pageable);

    Optional<Task> findById(Long id);

}
