package com.kosa.chanzipup.config.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    private final Map<TokenType, String> keyMap;

    public JwtProvider(@Value("${jwt.access.key}") String accessKey, @Value("${jwt.refresh.key}") String refreshKey) {
        keyMap = Map.of(
                TokenType.ACCESS, accessKey,
                TokenType.REFRESH, refreshKey
        );
    }

    public String extractEmail(String token, TokenType type) {
        return extractClaim(token, Claims::getSubject, type);
    }

    public Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, Claims::getExpiration, type);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, TokenType type) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type) {

        return Jwts.parser()
                .setSigningKey(getSignKey(keyMap.get(type)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, TokenType type) {
        Date extractExpiration = extractExpiration(token, type);
        Date now = new Date();
        return extractExpiration.before(now);
    }

    public Boolean validateToken(String token, UserDetails userDetails, TokenType type) {
        final String email = extractEmail(token, type);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }

    public String generateToken(String username,
                                TokenType tokenType,
                                LocalDateTime expireDate) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, expireDate, tokenType);
    }

    private String createToken(Map<String, Object> claims, String username,
                               LocalDateTime expireDate, TokenType tokenType) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600))) // 30분동안 토큰 유지
                .signWith(getSignKey(keyMap.get(tokenType)), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
