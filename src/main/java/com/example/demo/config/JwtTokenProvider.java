package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Users;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

	@Value("${jwtkey}")
	private String jwtSecret;

	private long jwtExpirationDate = 604800000;

	// generate JWT token
	public String generateToken(Authentication authentication, Users user) {
		Map<String, Object> claims = new HashMap<>();
		String username = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date()).expiration(expireDate)
				.and().signWith(key()).compact();

		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from JWT token
	public String getUsername(String token) {

		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	// validate JWT token
	public boolean validateToken(String token) {
		Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
		return true;

	}

}
