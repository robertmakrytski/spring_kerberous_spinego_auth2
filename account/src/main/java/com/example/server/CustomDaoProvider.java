package com.example.server;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomDaoProvider extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	private UserService userService;
	@Autowired
	Map<String, String> mapUsernames;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		return super.createSuccessAuthentication(user.getUsername(), authentication, user);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser = userService.getByUsername(username);
		return loadedUser;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = mapUsernames.get(((WebAuthenticationDetails) authentication.getDetails()).getSessionId());
		if (Objects.isNull(username))
			username = (String) authentication.getPrincipal();
		return createSuccessAuthentication(authentication.getPrincipal(), authentication, retrieveUser(username, null));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return super.supports(authentication);
	}
}