package com.circle_devs.zdanovskih.config;

import com.circle_devs.zdanovskih.entity.Role;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;

/**
 * Security configuration.
 * Grants access to rest endpoints
 */
@Configuration
public class AppSecurityConfig {

    /**
     * Grants access: members of group USER - for read,  members of group ADMIN - full control
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception - BadCredentialsException
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(REST_MAP + "/**"))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, REST_MAP + "/**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                        .requestMatchers(REST_MAP + "/**").hasAnyAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}