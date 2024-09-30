package com.example.demo.Exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler implements AuthenticationFailureHandler{
	  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
	        logger.error("OAuth2 Authentication error: {}", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body("Authentication failed: " + ex.getMessage());
	    }

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			 logger.error("OAuth2 Authentication error: {}", exception.getMessage());
			
		}
}
