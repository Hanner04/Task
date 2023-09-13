package com.proyect.task.repository;

import com.proyect.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITaskRepository extends JpaRepository<Task,Long> {
}
