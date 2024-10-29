package com.spacesinspace.springsecurityassignment.config;

import com.spacesinspace.springsecurityassignment.exception.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthFailHandler authFailHandler;

    @Autowired
    public SecurityConfig(AuthFailHandler authFailHandler) {
        this.authFailHandler = authFailHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filerChainConfigure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/").permitAll();
            auth.requestMatchers("/admin/*").hasAnyAuthority("ADMIN");
            auth.requestMatchers("/user/*").hasAnyAuthority("USER", "ADMIN");
            auth.anyRequest().authenticated();
        }).formLogin(login -> {
            login.loginPage("/auth/login");
            login.usernameParameter("username");
            login.passwordParameter("password");
            login.defaultSuccessUrl("/",true);
            login.failureHandler(authFailHandler);
        }).logout(logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSESSIONID");
            logout.invalidateHttpSession(true);
            logout.logoutSuccessUrl("/");
        }).csrf(csrf -> csrf.disable());

        return http.build();
    }
}
