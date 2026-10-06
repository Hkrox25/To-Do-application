package com.hkrox.todoproj.services;

import com.hkrox.todoproj.models.Priority;
import com.hkrox.todoproj.models.Task;
import com.hkrox.todoproj.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    public void createTask(String title, Priority priority, String category) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setPriority(priority);
        task.setCategory(category == null || category.isBlank() ? "General" : category.trim());

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getActiveTask(id);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void restoreTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setDeletedAt(null);
        taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return getActiveTask(id);
    }

    public void updateTask(Long id, String title, Priority priority, String category) {
        Task task = getActiveTask(id);
        task.setTitle(title);
        task.setPriority(priority);
        task.setCategory(category);
        taskRepository.save(task);
    }

    public void toggleTask(Long id) {
        Task task = getActiveTask(id);
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    private Task getActiveTask(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
