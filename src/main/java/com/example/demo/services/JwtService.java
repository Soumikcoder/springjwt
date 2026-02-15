package com.example.demo.services;

import java.security.KeyPair;
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
    final long accessTokenValidity = 1000 * 60 * 3;
    final long refreshTokenValidity = 1000 * 60 * 20;

    public JwtService() {
        keyPair = SIG.ES256.keyPair().build();
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claim = new HashMap<>();
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return Jwts.builder()
                .claims(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(keyPair.getPrivate())
                .compact();

    }

    public String extractUsername(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // TODO Auto-generated method stub
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // TODO Auto-generated method stub
        return (Claims) Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parse(token)
                .getPayload();
    }

    public boolean validateToken(UserDetails userDetails, String token) {
        // TODO Auto-generated method stub
        final String username = extractUsername(token);
        return userDetails.getUsername().equalsIgnoreCase(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // TODO Auto-generated method stub
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getExpiration);
    }

}
