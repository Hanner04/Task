package com.proyect.task.controller.user;

import com.proyect.task.model.auth.AuthRequest;
import com.proyect.task.model.auth.AuthResponse;
import com.proyect.task.model.auth.UserInfo;
import com.proyect.task.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/addUser")
    public ResponseEntity<AuthResponse> addUser(@RequestBody UserInfo request)
    {
        return ResponseEntity.ok(authService.addUser(request));
    }
}
