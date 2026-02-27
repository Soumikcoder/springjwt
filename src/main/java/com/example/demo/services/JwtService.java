package com.example.demo.services;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Service
public class JwtService {
    private static final String FILE_NAME = "JWT-keys.properties";
    KeyPair keyPair;
    final long accessTokenValidity = 1000 * 60 * 60;
    final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 2;

    public JwtService() {
        try {
            Path path = Paths.get(FILE_NAME);

            if (Files.exists(path)) {
                loadKeysFromFile(path);
                System.out.println("Loaded keys from file");
            } else {
                generateAndSaveKeys(path);
                System.out.println("Getnerated and Saved keys");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize keys", e);
        }
    }

    private void generateAndSaveKeys(Path path) throws Exception {

        keyPair = SIG.ES256.keyPair().build();

        Properties props = new Properties();
        String publicKey = Base64.getEncoder()
                .encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder()
                .encodeToString(keyPair.getPrivate().getEncoded());

        props.setProperty("public", publicKey);
        props.setProperty("private", privateKey);

        try (OutputStream os = Files.newOutputStream(path)) {
            props.store(os, "JWT Keys");
        }
    }

    private void loadKeysFromFile(Path path) throws Exception {

        Properties props = new Properties();

        try (InputStream is = Files.newInputStream(path)) {
            props.load(is);
        }

        byte[] publicBytes = Base64.getDecoder().decode(props.getProperty("public"));
        byte[] privateBytes = Base64.getDecoder().decode(props.getProperty("private"));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        PublicKey publicKey = keyFactory.generatePublic(
                new X509EncodedKeySpec(publicBytes));

        PrivateKey privateKey = keyFactory.generatePrivate(
                new PKCS8EncodedKeySpec(privateBytes));

        keyPair = new KeyPair(publicKey, privateKey);
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
