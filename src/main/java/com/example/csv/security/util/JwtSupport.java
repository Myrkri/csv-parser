package com.example.csv.security.util;

import com.example.csv.model.JwtTokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtSupport {

    private final JwtTokenProperties jwtTokenProperties;

    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(final Map<String, Object> claims, final String subject) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime() + jwtTokenProperties.getExpirationTime());
        final SecretKey secret = Keys.hmacShaKeyFor(jwtTokenProperties.getSecretKey().getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(secret, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(final String token) {
        final SecretKey secret = Keys.hmacShaKeyFor(jwtTokenProperties.getSecretKey().getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(final String token) {
        final SecretKey secret = Keys.hmacShaKeyFor(jwtTokenProperties.getSecretKey().getBytes());
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}

