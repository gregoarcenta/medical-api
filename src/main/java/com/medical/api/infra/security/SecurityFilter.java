package com.medical.api.infra.security;

import com.medical.api.infra.security.exceptions.UserDisabledException;
import com.medical.api.infra.security.exceptions.UserNotFoundException;
import com.medical.api.repositories.UserRepository;
import com.medical.api.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilter(
            TokenService tokenService, UserRepository userRepository,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = getToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String subject = tokenService.getSubject(token);

            var userOptional = userRepository.findByUsername(subject);
            var authentication = buildAuthentication(userOptional);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, ex);
        }
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(Optional<UserDetails> userOptional) {
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        var user = userOptional.get();

        if (!user.isEnabled()) {
            throw new UserDisabledException();
        }

        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
    }

    private String getToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
