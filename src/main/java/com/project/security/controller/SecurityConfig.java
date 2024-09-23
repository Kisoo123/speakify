package com.project.security.controller;

import com.project.security.service.MyUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    public UserDetailsService userDetailsService() {
        return userDetailsService; // 커스텀 UserDetailsService 빈을 사용 (생성자 주입으로 전달된 userDetailsService)
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MyUserDetailsService userDetailsService) throws Exception {
        http.authorizeHttpRequests((authz) -> authz // 최신 방식으로 변경
                        .requestMatchers("/","/search", "/register", "/login","/login?error", "/resources/**", "/static/**", "/home", "/css/**", "/js/**", "/images/**").permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.INCLUDE).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            // 로그인 실패 시 예외 로그 출력
                            System.out.println("일단은 핸들러");
                            exception.printStackTrace();
                            response.sendRedirect("/login?error");
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe
                        .userDetailsService(userDetailsService)  // UserDetailsService 명시적으로 설정
                        .key("uniqueAndSecret") // 쿠키 암호화를 위한 고유한 키
                        .rememberMeParameter("remember-me")
                )
                .requestCache(RequestCacheConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, MyUserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/home");
    }


}
