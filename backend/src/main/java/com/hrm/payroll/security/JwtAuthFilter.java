package com.hrm.payroll.security;

import com.hrm.payroll.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Allow CORS preflight (OPTIONS) requests to bypass authentication.
        // Browsers send OPTIONS before actual requests; blocking it results in 403 and CORS failure.
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);

            // 🔹 No token → continue
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 🔹 Extract data from JWT
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            // 🔹 Validate and set authentication
            if (username != null
                    && role != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtService.validateToken(token, username)) {

                UsernamePasswordAuthenticationToken authToken =
                        buildAuthentication(request, username, role);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception ex) {
            log.warn("JWT authentication failed: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract token from Authorization header
     */
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * Build Spring Security authentication object
     */
    private UsernamePasswordAuthenticationToken buildAuthentication(
            HttpServletRequest request,
            String username,
            String role) {

        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(authority)
                );

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        return authToken;
    }
}