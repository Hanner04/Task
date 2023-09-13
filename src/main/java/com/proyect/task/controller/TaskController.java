package com.proyect.task.controller;

import com.proyect.task.config.util.TokenUtils;
import com.proyect.task.model.AuthRequest;
import com.proyect.task.model.Task;
import com.proyect.task.model.UserInfo;
import com.proyect.task.service.TaskService;
import com.proyect.task.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserInfoService userService;

    @Autowired
    private TokenUtils jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public UserInfo addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(jwt);
    }
    @GetMapping("/getAllTasks")
    @Operation(summary = "Get All Task")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping()
    @Operation(summary = "Create Task")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
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
    public Task updateTask(@Valid @PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/deleteTask/{id}")
    @Operation(summary = "Delete Task")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Valid @PathVariable Long id) {
        taskService.deleteTask(id);
    }


}
