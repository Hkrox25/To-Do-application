package com.hkrox.todoproj.controller;

import com.hkrox.todoproj.dto.TaskForm;
import com.hkrox.todoproj.models.Priority;
import com.hkrox.todoproj.models.Task;
import com.hkrox.todoproj.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();

        List<Task> activeTasks = tasks.stream()
                .filter(task -> !task.isCompleted())
                .toList();

        List<Task> completedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .toList();

        Long completedCount = tasks.stream()
                .filter(Task::isCompleted)
                .count();


        model.addAttribute("tasks", tasks);
        model.addAttribute("activeTasks", activeTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("priorities",Priority.values());

        return "tasks";
    }

    @PostMapping
    public String createTask(
            @Valid @ModelAttribute("taskForm") TaskForm taskForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", validationMessage(bindingResult));
            return "redirect:/tasks";
        }

        taskService.createTask(
                taskForm.getTitle().trim(),
                taskForm.getPriority(),
                taskForm.getCategory().trim());
        redirectAttributes.addFlashAttribute("success", "Task added successfully.");
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTask(id);
        TaskForm taskForm = new TaskForm();
        taskForm.setTitle(task.getTitle());
        taskForm.setPriority(task.getPriority() == null ? Priority.MEDIUM : task.getPriority());
        taskForm.setCategory(task.getCategory() == null ? "General" : task.getCategory());

        model.addAttribute("task", task);
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("priorities", Priority.values());
        return "edit-task";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(
            @PathVariable Long id,
            @Valid @ModelAttribute("taskForm") TaskForm taskForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", validationMessage(bindingResult));
            return "redirect:/tasks/" + id + "/edit";
        }

        taskService.updateTask(
                id,
                taskForm.getTitle().trim(),
                taskForm.getPriority(),
                taskForm.getCategory().trim());
        redirectAttributes.addFlashAttribute("success", "Task updated successfully.");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("success", "Task deleted. You can undo this action.");
        redirectAttributes.addFlashAttribute("deletedTaskId", id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/restore")
    public String restoreTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.restoreTask(id);
        redirectAttributes.addFlashAttribute("success", "Task restored.");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.toggleTask(id);
        redirectAttributes.addFlashAttribute("success", "Task status updated.");

        return "redirect:/tasks";
    }

    private String validationMessage(BindingResult bindingResult) {
        if (bindingResult.getFieldError() != null) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return "Please correct the highlighted task fields.";
    }
}
