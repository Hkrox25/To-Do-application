package com.hkrox.todoproj.repository;

import com.hkrox.todoproj.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    Optional<Task> findByIdAndDeletedAtIsNull(Long id);
}
