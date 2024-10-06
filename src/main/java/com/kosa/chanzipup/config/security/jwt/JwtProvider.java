package com.kosa.chanzipup.config.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    // accessToken 유효 시간 30분
    private static final int ACCESS_TOKEN_EXPIRE_AMOUNT = 100;
    // refreshToken 유효 시간 7일
    private static final int REFRESH_TOKEN_EXPIRE_AMOUNT = 24 * 60 * 30;

    private final Map<TokenType, String> keyMap;
    private final Map<TokenType, Integer> expireAmount;

    public JwtProvider(@Value("${jwt.access.key}") String accessKey,
                       @Value("${jwt.refresh.key}") String refreshKey) {
        keyMap = Map.of(
                TokenType.ACCESS, accessKey,
                TokenType.REFRESH, refreshKey
        );

        expireAmount = Map.of(
                TokenType.ACCESS, ACCESS_TOKEN_EXPIRE_AMOUNT,
                TokenType.REFRESH, REFRESH_TOKEN_EXPIRE_AMOUNT
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
                                LocalDateTime issuedDate) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, issuedDate, tokenType);
    }

    private String createToken(Map<String, Object> claims, String username,
                               LocalDateTime issuedDate, TokenType tokenType) {
        // 1. 기간을 설정해서 발급한다.
        Date issuedAt = convertDate(issuedDate);
        Date expriationDate = convertDate(issuedDate.plusMinutes(expireAmount.get(tokenType)));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expriationDate) // 30분동안 토큰 유지
                .signWith(getSignKey(keyMap.get(tokenType)), SignatureAlgorithm.HS256).compact();

        return tokenType.changeToken(token);
    }

    private Date convertDate(LocalDateTime createDate) {
        return Date.from(createDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getSignKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
