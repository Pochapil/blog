package ru.bagration.spring.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.bagration.spring.service.ApplicationUserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService applicationUserService;
    private final JwtTokenVerifier tokenVerifier;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().configurationSource(getCorsConfiguration()).and().csrf().disable()
                .addFilter(getAuthenticationFilter())
                .addFilterAfter(tokenVerifier, AuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/user/**").hasAnyAuthority("user", "admin")
                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers("/test/cors").permitAll()
                .anyRequest().permitAll();

    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        var authenticationFilter = new AuthenticationFilter(applicationUserService, authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/login");
        return authenticationFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserService).passwordEncoder(passwordEncoder);

    }

    private CorsConfigurationSource getCorsConfiguration(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
