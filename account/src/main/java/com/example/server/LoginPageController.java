package com.example.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginPageController {
	@GetMapping(value = { "login", "login1" })
	String login(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object exception = session.getAttribute("LAST_AUTH_EXCEPTION");
			if (exception != null) {
				model.addAttribute("errorMessage", exception.toString());
				session.removeAttribute("LAST_AUTH_EXCEPTION");
			}
		}
		model.addAttribute("clientUrl", "http://gdev.test/account/public/loginAD");
		return "login";
	}
}
