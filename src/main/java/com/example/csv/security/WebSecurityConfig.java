package com.example.csv.security;

import com.example.csv.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    protected DefaultSecurityFilterChain filter(HttpSecurity http) throws Exception {
        log.info("Authorization Starts!");
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/me").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/v1/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
