package com.hkrox.todoproj.dto;

import com.hkrox.todoproj.models.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskForm {

    @NotBlank(message = "Task title is required.")
    @Size(max = 200, message = "Task title must be 200 characters or fewer.")
    private String title;

    @NotNull(message = "Please select a priority.")
    private Priority priority = Priority.MEDIUM;

    @NotBlank(message = "Category is required.")
    @Size(max = 50, message = "Category must be 50 characters or fewer.")
    private String category = "General";
}
