package by.petrovich.taskwizard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_seq", allocationSize = 5)
    @Column(columnDefinition = "bigint")
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    @Column(length = 350, nullable = false)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(name = "fk_task_status"))
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", nullable = false, foreignKey = @ForeignKey(name = "fk_task_priority"))
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_author"))
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee_id", foreignKey = @ForeignKey(name = "fk_assignee"))
    private User assignee;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<TaskComment> taskComments;

}
