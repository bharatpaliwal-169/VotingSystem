package com.evs.init.config;

import com.evs.init.service.UserService;
import com.evs.init.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter intercepts requests to check for JWT tokens.
 */
@Component // Spring component
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Extends OncePerRequestFilter to ensure it's executed once per request

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization"); // Gets the Authorization header

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // Checks if header starts with "Bearer "
            jwt = authorizationHeader.substring(7); // Extracts the token
            username = jwtUtil.extractUsername(jwt); // Extracts username from token
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // If username is present and no authentication
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Loads user details
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) { // Validates the token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); // Creates authentication token
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Sets details
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Sets authentication in context
            }
        }
        chain.doFilter(request, response); // Continues the filter chain
    }
}