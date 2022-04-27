package ru.bagration.spring.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.trim().replace(SecurityUtils.tokenPrefix, " ");

        try{

            var claims = Jwts.parser()
                    .setSigningKey(SecurityUtils.secretKey)
                    .parseClaimsJws(token);

            var body = claims.getBody();
            var username = (String) body.get("username");
            var authoritiesString = (String) body.get("authorities");
            var parsedAuthorities = authoritiesString.split(",");
            var authorities = new HashSet<SimpleGrantedAuthority>();

            for (var authority: parsedAuthorities){
                authorities.add(new SimpleGrantedAuthority(authority));
            }

            var authentication = new UsernamePasswordAuthenticationToken(username, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);



        }catch (JwtException ex){
            throw new IllegalStateException(String.format("Token %s can not be trusted", token));
        }


    }
}
