package ru.bagration.spring.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bagration.spring.service.ApplicationUserService;

@RequiredArgsConstructor
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final ApplicationUserService applicationUserService;
    private final JwtTokenVerifier tokenVerifier;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and().csrf().disable()
                .addFilter(getAuthenticationFilter())
                .addFilterAfter(tokenVerifier, AuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/users**").hasAuthority("user")
                .antMatchers("/admin**").hasAuthority("admin")
                .antMatchers().permitAll();


    }

    private AuthenticationFilter getAuthenticationFilter() {

        var authenticationFilter = new AuthenticationFilter(applicationUserService);
        authenticationFilter.setFilterProcessesUrl("/login");
        return authenticationFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserService).passwordEncoder(encoder);

    }


}
