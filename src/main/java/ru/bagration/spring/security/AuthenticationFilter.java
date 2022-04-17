package ru.bagration.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.bagration.spring.dto.security.LoginRequest;
import ru.bagration.spring.service.ApplicationUserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ApplicationUserService applicationUserService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
        String username =((User)authentication.getPrincipal()).getUsername();
        var map = new HashMap<String, String>();
        map.put("message","success");
        response.getWriter().write(new ObjectMapper().writeValueAsString(map));
    }

}
