package com.example.CCINETApplication.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CCINETApplication.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndUser_Id(Long id, Long userId);
    List<Task> findByUser_Id(Long userId);
}