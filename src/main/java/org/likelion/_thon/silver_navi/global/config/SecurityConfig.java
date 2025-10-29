package org.likelion._thon.silver_navi.global.config;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.global.auth.exception.CustomAccessDeniedHandler;
import org.likelion._thon.silver_navi.global.auth.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // swagger
                        .requestMatchers("/docs", "/docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 사용자
                        .requestMatchers("/api/users/signup","/api/users/*").permitAll()
                        // 시설 관리자
                        .requestMatchers("/api/managers/check-id",
                                "/api/managers/signup", "/api/managers/signin").permitAll()
                        // 제휴코드
                        .requestMatchers("/api/code/create").permitAll()
                        .anyRequest().authenticated()
                )
                        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(ex -> ex
                                .accessDeniedHandler(customAccessDeniedHandler)
                );
        return http.build();
    }
}
