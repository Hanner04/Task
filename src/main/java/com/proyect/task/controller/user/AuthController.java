package com.proyect.task.controller.user;

import com.proyect.task.model.auth.AuthRequest;
import com.proyect.task.model.auth.UserInfo;
import com.proyect.task.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Get Token")
    public ResponseEntity<Object> login(@RequestBody AuthRequest request)
    {
        return authService.login(request);
    }

    @PostMapping("/addUser")
    @Operation(summary = "Create user")
    public ResponseEntity<Object>  addUser(@RequestBody UserInfo request)
    {
        return authService.addUser(request);
    }

    @GetMapping("/getAllUser")
    @Operation(summary = "Get All User")
    @ResponseStatus(HttpStatus.OK)
    public List<UserInfo> getAllTasks() {
        return authService.getAllUser();
    }
}
