package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.dto.JwtAuthResponse;
import com.example.demo.dto.LoginDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsServices;

import ch.qos.logback.core.net.LoginAuthenticator;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		DefaultOAuth2User userAuth = (DefaultOAuth2User) authentication.getPrincipal();
		OAuth2AuthenticationToken authentictionToken = (OAuth2AuthenticationToken) authentication;
		String authorizationId = authentictionToken.getAuthorizedClientRegistrationId();
		String email = null;
		String name = null;
		if (authorizationId.equalsIgnoreCase("google")) {
			email = userAuth.getAttribute("email");
			name = userAuth.getAttribute("name");
		} else if (authorizationId.equalsIgnoreCase("github")) {
			email = userAuth.getAttribute("email") != null ? userAuth.getAttribute("email")
					: userAuth.getAttribute("login").toString() + "@gmail.com";
			name = userAuth.getAttribute("name");

		}

		Users user = userRepository.findByUserName(email);
		// Save LOgin Data
		if (user == null) {
			Users newUser = new Users();
			newUser.setEmail(email);
			newUser.setGender("P");
			newUser.setMobile("");
			newUser.setUsername(email);
			newUser.setPlain_pass("");
			newUser.setName(name);
			newUser.setProvider(authorizationId);
			newUser.setPassword(encoder.encode("AUTH_PASS"));
			newUser.setPlain_pass("AUTH_PASS");
			List<Role> role = new ArrayList<>();
			role.add(roleRepository.findByName("SOCIAL_LOGIN"));
			newUser.setRoles(role);
			userRepository.save(newUser);
		}
		if (authorizationId.equalsIgnoreCase("google"))
			response.sendRedirect("/user/loginWithGoogle");
		else if (authorizationId.equalsIgnoreCase("github"))
			response.sendRedirect("/user/loginWithGitHub");
	}

}
