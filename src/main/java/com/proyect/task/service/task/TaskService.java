package com.proyect.task.service.task;

import com.proyect.task.model.auth.AuthResponse;
import com.proyect.task.model.task.Task;
import com.proyect.task.repository.ITaskRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public ResponseEntity<Object>  createTask(Task task) {
        AtomicReference<String> message = new AtomicReference<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        try {

            if (!violations.isEmpty()) {
                violations.forEach(violation -> message.set(violation.getMessage()));
                LOGGER.error("Error: Create task: {} ", message);
                return ResponseEntity.status(401).body(AuthResponse
                        .builder()
                        .code(401)
                        .message(message.toString())
                        .build());
            } else {
                LOGGER.info("INFO: Create task: {}", task);
                taskRepository.save(task);
                return ResponseEntity.status(200).body(AuthResponse
                        .builder()
                        .code(200)
                        .message("Task added successfully")
                        .build());

            }

        } catch (Exception e) {
            LOGGER.error("Error: Create task: {} ", "Duplicate key. This task is already registered.");
            return ResponseEntity.status(401).body(AuthResponse
                    .builder()
                    .code(401)
                    .message("Duplicate task. This user is already registered.")
                    .build());
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Object>  updateTask(Long id, Task task) {

        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            LOGGER.info("UPDATE task: {}", task);
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            taskRepository.save(existingTask);

            return ResponseEntity.status(200).body(AuthResponse
                    .builder()
                    .code(200)
                    .message("Task update successfully")
                    .build());
        }else {
            LOGGER.error("Task not updated");
            return ResponseEntity.status(404).body(AuthResponse
                    .builder()
                    .code(404)
                    .message("Task not updated.")
                    .build());
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
