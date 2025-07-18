package com.SpringSecurity.config;

import com.SpringSecurity.security.CustomerUserDetailsService;
import com.SpringSecurity.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Spring Security filter chain configured ✅");
        return http
                .csrf(cusomizer -> cusomizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST , "/api/auth/post").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()

                        .requestMatchers(HttpMethod.GET , "/api/auth/whoami").authenticated() // protect explicitly
                        .requestMatchers(HttpMethod.GET , "/api/auth/all").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT , "/api/auth/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE , "/api/auth/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(customerUserDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }

}
