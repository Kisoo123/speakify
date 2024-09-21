package com.project.security.controller;

import com.project.security.service.MyUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz // 최신 방식으로 변경
                        .requestMatchers("/","/register", "/login", "/resources/**","/home").permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.INCLUDE).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ).requestCache(RequestCacheConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService; // 커스텀 UserDetailsService 빈을 사용 (생성자 주입으로 전달된 userDetailsService)
    }

}
