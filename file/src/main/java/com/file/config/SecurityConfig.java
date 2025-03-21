package com.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		try {
			UserDetails user = User.builder()

					.username("admin").password(passwordEncoder().encode("password")).roles("USER").build();
			return new InMemoryUserDetailsManager(user);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing UserDetailsService: " + e.getMessage());
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		try {
			return new BCryptPasswordEncoder();
		} catch (Exception e) {
			throw new RuntimeException("Error initializing PasswordEncoder: " + e.getMessage());
		}
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		try {
			return authenticationConfiguration.getAuthenticationManager();
		} catch (Exception e) {
			throw new Exception("Error initializing AuthenticationManager: " + e.getMessage());
		}
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(
							auth -> auth.requestMatchers("/files/**").authenticated().anyRequest().permitAll())
					.httpBasic(Customizer.withDefaults())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

			return http.build();
		} catch (Exception e) {
			throw new Exception("Error initializing SecurityFilterChain: " + e.getMessage());
		}
	}
}
