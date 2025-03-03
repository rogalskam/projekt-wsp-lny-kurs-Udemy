package com.example.auth.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    public final String SECRET;
    public final int  exp;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.exp}") int exp) {
        SECRET = secret;
        this.exp = exp;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Map<String, Object> clames = new HashMap<>();
        return createToken(clames, username);
    }

    private String createToken(Map<String, Object> clames, String username) {
        return Jwts.builder()
                .setClaims(clames)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
