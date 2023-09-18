package com.proyect.task.service.auth;

import com.proyect.task.model.auth.AuthRequest;
import com.proyect.task.model.auth.AuthResponse;
import com.proyect.task.model.auth.UserInfo;
import com.proyect.task.repository.IUserRepository;
import com.proyect.task.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(
                        request.getUsername())
                .orElse(null);
        if (user != null) {
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .status("success")
                    .message(token)
                    .build();
        }

        return AuthResponse.builder()
                .status("error")
                .message("User not found")
                .build();

    }

    public AuthResponse addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return AuthResponse.builder()
                .status("success")
                .message(jwtService.getToken(userInfo))
                .build();
    }


}
