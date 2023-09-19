package com.proyect.task.controller.task;

import com.proyect.task.model.task.Task;
import com.proyect.task.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/getAllTasks")
    @Operation(summary = "Get All Task")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping()
    @Operation(summary = "Create Task")
    public ResponseEntity<Object>  createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/getTaskById/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get by ID Task")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if(task != null){
            return ResponseEntity.ok(task);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateTask/{id}")
    @Operation(summary = "Update Task")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object>  updateTask(@Valid @PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/deleteTask/{id}")
    @Operation(summary = "Delete Task")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Valid @PathVariable Long id) {
        taskService.deleteTask(id);
    }


}
