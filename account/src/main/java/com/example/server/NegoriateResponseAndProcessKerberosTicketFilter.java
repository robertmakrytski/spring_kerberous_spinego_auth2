package com.example.server;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.kerberos.authentication.KerberosTicketValidation;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NegoriateResponseAndProcessKerberosTicketFilter extends OncePerRequestFilter {
	private static final String NTLMSSP_PREFIX = "Negotiate TlRMTVNTUA";
	@Autowired
	private SunJaasKerberosTicketValidator ticketValidator;
	/*
	 * This is very bad trick because i can't join AuthenticationProvider with
	 * oauth2 when i create filter like spinego and extends from
	 * DaoAuthenticationProvider - it doesn't work. I save session and understand
	 * username from this map where key - is sessionId , and value - is username
	 */
	@Autowired
	Map<String, String> usernamesMap;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// COPY from SpnegoAuthenticationProcessingFilter
		String header = request.getHeader("Authorization");
		boolean flagAd = Objects.nonNull(request.getParameter("AD"));
		String path = request.getRequestURI();
		if (header != null && ((header.startsWith("Negotiate ") && !header.startsWith(NTLMSSP_PREFIX))
				|| header.startsWith("Kerberos "))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Received Negotiate Header for request " + request.getRequestURL() + ": " + header);
			}
			byte[] base64Token = header.substring(header.indexOf(" ") + 1).getBytes("UTF-8");
			byte[] kerberosTicket = Base64.decode(base64Token);

			KerberosTicketValidation ticketValidation = ticketValidator.validateTicket(kerberosTicket);
			usernamesMap.put(request.getSession().getId(), ticketValidation.username());
			log.debug("Successfully validated " + ticketValidation.username());
		} else if ("POST".equals(request.getMethod()) && path.equals("/login") && flagAd) {
			log.info("found post and loging");
			response.addHeader("WWW-Authenticate", "Negotiate");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.flushBuffer();
			return;
		}
		filterChain.doFilter(request, response);
	}

}