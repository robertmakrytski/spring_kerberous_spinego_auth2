package com.example.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ConfigurationApp {
	  private final UserService userService;
//	  @Bean
//	    PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }
//	  @Bean
//	    AuthenticationProvider authenticationProvider() {
//	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	        authProvider.setUserDetailsService(userService.userDetailsService());
//	        authProvider.setPasswordEncoder(passwordEncoder());
//	        return authProvider;
//	    }
	  @Bean
		public UserDetailsService userDetailsService() {
		  
		  return userService.userDetailsService();
		}
		
		// Password encoder
//		@Bean
//	    public PasswordEncoder getPasswordEncoder() {
//	        return NoOpPasswordEncoder.getInstance();
//	    }
//		@Bean
//	    UserDetailsService users() {
//	        UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
//	                .username("admin")
//	                .password("password")
//	                .roles("USER")
//	                .build();
//	        return new InMemoryUserDetailsManager(user);
//	    }

}
