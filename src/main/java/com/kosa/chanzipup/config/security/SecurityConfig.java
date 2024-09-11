package com.kosa.chanzipup.config.security;

import com.kosa.chanzipup.config.security.filter.FormLoginAuthenticationFilter;
import com.kosa.chanzipup.config.security.filter.JwtAuthenticationFilter;

import com.kosa.chanzipup.config.security.userdetail.oauth2.Oauth2MemberService;

import com.kosa.chanzipup.config.security.userdetail.success.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
                                                   ProviderManagerBuilder builder, LoginSuccessHandler loginSuccessHandler,
                                                   AuthenticationManager manager) throws Exception {

        FormLoginAuthenticationFilter formLoginAuthenticationFilter = setFormLoginAuthentication(loginSuccessHandler, manager);

        // 기본 설정
        http
                .authorizeHttpRequests(request -> request
<<<<<<< login-feature
                        .requestMatchers("/api/payment/**", "/api/memberships/**").hasRole("COMPANY")
=======
                        .requestMatchers("/api/memberships/**").hasRole("COMPANY")
>>>>>>> main
                        .requestMatchers("/api/**", "/", "/oauth2/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .addFilterAt(formLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
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

    private FormLoginAuthenticationFilter setFormLoginAuthentication(LoginSuccessHandler loginSuccessHandler, AuthenticationManager manager) {
        FormLoginAuthenticationFilter formLoginAuthenticationFilter = new FormLoginAuthenticationFilter();
        formLoginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/form/login/**", "POST"));


        formLoginAuthenticationFilter.setAuthenticationManager(manager);
        formLoginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return formLoginAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
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


    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(true);

        return providerManager;
    }
}
