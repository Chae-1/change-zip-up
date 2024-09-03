package com.kosa.chanzipup.config.security;

import com.kosa.chanzipup.config.security.filter.JwtAuthenticationFilter;
import com.kosa.chanzipup.config.security.userdetail.oauth2.Oauth2MemberService;
import com.kosa.chanzipup.config.security.userdetail.oauth2.success.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   Oauth2MemberService oauth2MemberService) throws Exception {
        // 기본 설정
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/test").authenticated()
                        .requestMatchers("/api/**", "/", "/oauth2/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));


        // Oauth2 Login 설정
        http.oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(redirection ->
                        redirection.baseUri("/api/members/oauth/*"))
                .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint
                                .userService(oauth2MemberService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .permitAll());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("/*");


        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", configuration);
        return configSource;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
