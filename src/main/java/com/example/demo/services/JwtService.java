package com.example.demo.services;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Service
public class JwtService {
    KeyPair keyPair;
    final long accessTokenValidity = 1000 * 60 * 60;
    final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 2;
    public static final byte[] SEED = new byte[] { (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x9A, (byte)0xBC, (byte)0xDE, (byte)0xF0, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88, (byte)0x99, (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF, (byte)0x00, (byte)0xAB, (byte)0xCD, (byte)0xEF, (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x90 };
    public JwtService() {
        keyPair = SIG.ES256.keyPair().random(new SecureRandom(SEED)).build();
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claim = new HashMap<>();
        return Jwts.builder()
                .claims(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(keyPair.getPrivate())
                .compact();

    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claim = new HashMap<>();
        return Jwts.builder()
                .claims(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(keyPair.getPrivate())
                .compact();

    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parse(token)
                .getPayload();
    }

    public boolean validateToken(UserDetails userDetails, String token) {
        final String username = extractUsername(token);
        return userDetails.getUsername().equalsIgnoreCase(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
