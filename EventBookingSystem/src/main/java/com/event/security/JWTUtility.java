package com.event.security;

import java.util.Date;



import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTUtility {
	private final String SECRET_KEY = "KarthikDarshadChaituAfzalPremKishorVishnuParshuSharathLokeshSammeerUdayIdiot";
	private final long TOKEN_EXPIRY_DURATION = 3600000L;

	private SecretKey getSecretKey() {
	    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String createToken(String username) {
		String token = Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY_DURATION)).signWith(getSecretKey())
				.compact();
		return token;
	}

	public String getUserNameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public boolean isTokenNotExpired(String token) {
		Date expirtyTime = Jwts.parser().verifyWith(getSecretKey())
				            .build()
				            .parseSignedClaims(token)
				            .getPayload()
				            .getExpiration();

		return expirtyTime.after(new Date());
	}


	public boolean isValidToken(String token, String requestedUser) {
		String userIdFromToken = getUserNameFromToken(token);
		return userIdFromToken.equalsIgnoreCase(requestedUser) && isTokenNotExpired(token);
	}
}
