package com.example.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class ConfigurationApp {

	private final UserService userService;

	ConfigurationApp(UserService userService) {
		this.userService = userService;
	}

	/*
	 * config for oauth2 which we can change
	 */
	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer
				.authorizationServer();
		http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher()).with(authorizationServerConfigurer,
				(authorizationServer) -> authorizationServer.oidc(Customizer.withDefaults()) // Enable OpenID Connect
																								// 1.0
		).authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())

				.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML))

				);
		return http.build();
	}

	/*
	 * Additional config where we can configurate additional security chain
	 */
	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {
		// switch off csrf for our custom page, to simplify protection
		http.csrf(c -> c.ignoringRequestMatchers("/login", "/public/loginAD"));
		http.authorizeHttpRequests(
				(authorize) -> authorize.requestMatchers("/login").permitAll().anyRequest().authenticated())
				.formLogin(f -> f.loginPage("/login").permitAll()).formLogin(Customizer.withDefaults())
				// add filter for SpnegoEntryPoint
				.addFilterBefore(tf(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	NegoriateResponseAndProcessKerberosTicketFilter tf() {
		return new NegoriateResponseAndProcessKerberosTicketFilter();
	}

	@Bean
	SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
		SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
		ticketValidator.setServicePrincipal("HTTP/gdev.test@GDEV.TEST");
		ticketValidator.setKeyTabLocation(new FileSystemResource("gdev04062025.keytab"));
		ticketValidator.setDebug(true);
		return ticketValidator;
	}

	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		DaoAuthenticationProvider d = new DaoAuthenticationProvider();
		d.setUserDetailsService(userService::getByUsername);
		return http.getSharedObject(AuthenticationManagerBuilder.class).authenticationProvider(d)
				.authenticationProvider(kt()).build();
	}

	@Bean("usernamesMap")
	Map<String, String> mapUsernames() {
		return new HashMap<>();
	}

	@Bean
	CustomDaoProvider kt() {
		return new CustomDaoProvider();
	}

}
