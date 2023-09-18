package com.proyect.task.service.task;

import com.proyect.task.model.task.Task;
import com.proyect.task.repository.ITaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            LOGGER.info("UPDATE task: {}", task);
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            return taskRepository.save(existingTask);
        }
        LOGGER.error("Task not save");
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
