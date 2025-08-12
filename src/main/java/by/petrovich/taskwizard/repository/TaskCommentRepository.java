package by.petrovich.taskwizard.repository;

import by.petrovich.taskwizard.model.TaskComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findAll(Sort sort);

    Optional<TaskComment> findById(Long id);

}
