package com.kosa.chanzipup.config.security.filter;


import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import com.kosa.chanzipup.domain.account.Account;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.kosa.chanzipup.config.security.jwt.TokenType.ACCESS;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String REFRESH_URI = "/refreshToken";

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // jwt 조회
        String accessToken = getJwtFromRequest(request);
        // jwt 안에 있는 email을 조회한다.
        // email로 Account 정보를 조회한다.
        try {
            if (accessToken != null) {
                String email = jwtProvider.extractEmail(accessToken, ACCESS);
                // email이 존재하지 않거나, 인증 상태가 아니면,
                if (email != null || SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (jwtProvider.validateToken(accessToken, userDetails, ACCESS)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 인증 상태로 만든다.
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            String requestURI = request.getRequestURI();
            log.info("토큰 재발급 요청 여부 확인: {}", requestURI);
            if (!request.getMethod().equals("GET") || !REFRESH_URI.equals(requestURI)) {
                log.info("인증 실패!!");
                response.setStatus(401);
                return ;
            }
            filterChain.doFilter(request, response);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
