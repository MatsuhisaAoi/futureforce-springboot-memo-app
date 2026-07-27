package com.lesson.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lesson.memo.security.AdminDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AdminDetailService adminDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
	        .authorizeHttpRequests(auth -> auth
	        	    .requestMatchers(
	        	        "/css/**", "/js/**", "/images/**",
	        	        "/admin/signup", "/admin/login"
	        	    ).permitAll()
	        	    .anyRequest().authenticated()
	        	)

            .userDetailsService(adminDetailService)
            .formLogin(login -> login
                .loginPage("/admin/signup")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/memo", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .permitAll()
            );

        return http.build();
    }
}
