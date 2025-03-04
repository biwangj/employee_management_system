package com.example.employee_management_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/update/**", "/employees/update-employee/**", "/update-employee/**").permitAll() // Public endpoints

                        .requestMatchers("/login", "/register", "/employees/delete/**", "/employees/delete").permitAll()
                        .requestMatchers("/employees/update-employee/**").permitAll() // Allow update page

                        .anyRequest().permitAll() // Protect other endpoints


                )
                .formLogin(login -> login
                        .loginPage("/login") // Ensure you have a /login page mapped in your controller
                        .defaultSuccessUrl("/dashboard", true) // Redirect after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Redirect after logout
                        .permitAll()
                );

        return http.build();
    }


//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        return provider;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("admin")
//                .password("{noop}admin") // NoOpPasswordEncoder for testing purposes
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
