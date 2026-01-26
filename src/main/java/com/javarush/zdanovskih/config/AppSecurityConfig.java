package com.javarush.zdanovskih.config;

import com.javarush.zdanovskih.entity.Role;
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

import static com.javarush.zdanovskih.constant.Const.REST_MAP;
import static com.javarush.zdanovskih.constant.Const.WEB_MAP;

@Configuration
public class AppSecurityConfig {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(REST_MAP + "/**"))
                .authorizeHttpRequests(auth -> auth
                        //REST
                        .requestMatchers(HttpMethod.GET, REST_MAP + "/**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                        .requestMatchers(REST_MAP + "/**").hasAnyAuthority(Role.ADMIN.getAuthority())
                        //WEB
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers(HttpMethod.GET,"/welcome")
                        .hasAnyAuthority(Role.ADMIN.getAuthority(),Role.USER.getAuthority())
                        //WEB - read for users
                        .requestMatchers(HttpMethod.GET,"/books", "/authors", "/publishers").authenticated()
                        //WEB - full control for admins
                        .requestMatchers("/books/**", "/authors/**", "/publishers/**","/authors/put/**").hasAnyAuthority(Role.ADMIN.getAuthority())

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome", true)
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults()); //For REST
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return delegatingPasswordEncoder;
    }

}