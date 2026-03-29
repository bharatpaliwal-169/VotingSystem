package com.evs.init.config;

import com.evs.init.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig configures Spring Security for the application.
 */
@Configuration // Marks this class as a configuration class
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain.
     */
    @Bean // Defines a bean for the security filter chain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disables CSRF protection for simplicity
            .authorizeHttpRequests() // Configures authorization for HTTP requests
            .requestMatchers("/api/auth/**").permitAll() // Allows unauthenticated access to auth endpoints
            .anyRequest().authenticated() // Requires authentication for all other requests
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sets session management to stateless
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adds JWT filter before username/password filter

        return http.build();
    }

    /**
     * Configures the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    /**
     * Provides a UserDetailsService implementation.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        };
    }

    /**
     * Provides a PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt for password encoding
    }
}