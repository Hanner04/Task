package com.proyect.task.config.util;

import com.proyect.task.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> tokenOptional = Optional.ofNullable(getTokenFromRequest(request));

        tokenOptional.ifPresent(token -> {
            String username = jwtService.getUsernameFromToken(token);

            Optional.ofNullable(username)
                    .filter(user -> SecurityContextHolder.getContext().getAuthentication() == null)
                    .ifPresent(user -> {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

                        Optional.of(jwtService.isTokenValid(token, userDetails))
                                .filter(isValid -> isValid)
                                .ifPresent(isValid -> {
                                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                            userDetails,
                                            null,
                                            userDetails.getAuthorities());

                                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                    SecurityContextHolder.getContext().setAuthentication(authToken);
                                });
                    });
        });

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);
    }
}
