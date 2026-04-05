package com.hrm.payroll.service.impl;

import com.hrm.payroll.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    // 🔐 Base64 encoded secret (application.properties)
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey key;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // 🔑 Initialize signing key
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // =========================================
    // 🔐 TOKEN GENERATION
    // =========================================
    @Override
    public String generateToken(String username, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // =========================================
    // 🔍 EXTRACTION
    // =========================================
    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        String role = extractAllClaims(token).get("role", String.class);
        return role != null ? role : "USER";
    }

    // =========================================
    // 🔎 VALIDATION (SECURE VERSION)
    // =========================================
    @Override
    public boolean validateToken(String token, String username) {
        try {
            Claims claims = extractAllClaims(token);

            String extractedUsername = claims.getSubject();
            Date expiration = claims.getExpiration();

            return extractedUsername.equals(username)
                    && expiration.after(new Date());

        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    // =========================================
    // 🔧 INTERNAL HELPER
    // =========================================
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}