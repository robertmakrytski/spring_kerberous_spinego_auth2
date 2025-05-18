package com.example.gateway;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class Test {
//	@Autowired
//	private RestClient restclient;
	//@RegisteredOAuth2AuthorizedClient("messaging-client-authorization-code")
	//OAuth2AuthorizedClient authorizedClient
//	@PostConstruct
	@Autowired
	private OAuth2AuthorizedClientManager authorizedClientManager;
	//
	@Autowired
	ClientRegistrationRepository clientRegistrationRepository;
	@PostConstruct
	public void init() {
		System.out.println("test" + clientRegistrationRepository.findByRegistrationId("messaging-client"));
		ClientRegistration cl = clientRegistrationRepository.findByRegistrationId("messaging-client");
//		cl.
//		OAuth2AuthorizeRequest.withClientRegistrationId("messaging-client").build();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("issuer-uri","http://127.0.0.1:9000");
//		OAuth2AccessTokenResponse atr = dp.getTokenResponse(new OAuth2ClientCredentialsGrantRequest(cl));
//		System.out.println(atr.toString());
//		ResponseEntity<String> s = restclient.post()
//				.uri("http://127.0.0.1:9000/oauth2/token")
//				.attributes(clientRegistrationId("messaging-client"))
//				.body()b.toEntity(String.class);
//		System.out.println(s);
	}
	@GetMapping("/test444")
	public String index(@RegisteredOAuth2AuthorizedClient("messaging-client") OAuth2AuthorizedClient authorizedClient) {
		System.out.println(authorizedClient.toString());
//		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("okta")
//				.principal(authentication)
//				.attributes(attrs -> {
//					attrs.put(HttpServletRequest.class.getName(), servletRequest);
//					attrs.put(HttpServletResponse.class.getName(), servletResponse);
//				})
//				.build();
//		OAuth2AuthorizedClient authorizedClient1 = this.authorizedClientManager.authorize(authorizeRequest);

//		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

		// ...

		return "index";
	}
	@GetMapping("/test")
	public ResponseEntity<String> test(@RegisteredOAuth2AuthorizedClient("messaging-client") OAuth2AuthorizedClient authorizedClient) {
		System.out.println(authorizedClient.toString());
		return ResponseEntity.ok("test");
	}
	
}
