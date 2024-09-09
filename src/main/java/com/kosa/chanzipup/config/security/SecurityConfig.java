package com.kosa.chanzipup.config.security;

import com.kosa.chanzipup.config.security.filter.FormLoginAuthenticationFilter;
import com.kosa.chanzipup.config.security.filter.JwtAuthenticationFilter;
import com.kosa.chanzipup.config.security.provider.LoginAuthenticationProvider;
import com.kosa.chanzipup.config.security.userdetail.oauth2.Oauth2MemberService;

import com.kosa.chanzipup.config.security.userdetail.oauth2.success.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final LoginSuccessHandler LoginSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   Oauth2MemberService oauth2MemberService,
                                                   LoginAuthenticationProvider provider, LoginSuccessHandler loginSuccessHandler) throws Exception {

        AuthenticationManager manager = new ProviderManager(List.of(provider));

        FormLoginAuthenticationFilter formLoginAuthenticationFilter = setFormLoginAuthentication(loginSuccessHandler, manager);

        // 기본 설정
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/**", "/", "/oauth2/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .addFilterAt(formLoginAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, FormLoginAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));


        // Oauth2 Login 설정
        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorization/**"))
                .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint
                                .userService(oauth2MemberService))
                .successHandler(LoginSuccessHandler)
                .permitAll());

        return http.build();
    }

    private FormLoginAuthenticationFilter setFormLoginAuthentication(com.kosa.chanzipup.config.security.userdetail.oauth2.success.LoginSuccessHandler loginSuccessHandler, AuthenticationManager manager) {
        FormLoginAuthenticationFilter formLoginAuthenticationFilter = new FormLoginAuthenticationFilter();
        formLoginAuthenticationFilter.setFilterProcessesUrl("/form/login");
        formLoginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/form/login/**", "POST"));

        formLoginAuthenticationFilter.setUsernameParameter("email");
        formLoginAuthenticationFilter.setPasswordParameter("password");
        formLoginAuthenticationFilter.setAuthenticationManager(manager);
        formLoginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return formLoginAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type"));
        configuration.setAllowCredentials(true);


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
