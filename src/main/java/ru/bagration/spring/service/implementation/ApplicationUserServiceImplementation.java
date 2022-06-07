package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bagration.spring.entity.ApplicationUser;
import ru.bagration.spring.repository.ApplicationUserRepository;
import ru.bagration.spring.repository.AuthorityRepository;
import ru.bagration.spring.repository.UserAuthorityRepository;
import ru.bagration.spring.security.UserDetailsImplementation;
import ru.bagration.spring.service.ApplicationUserService;

import java.util.ArrayList;
import java.util.HashSet;

import static ru.bagration.spring.utils.ResponseData.response;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImplementation implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        var userOptional = applicationUserRepository.findApplicationUserByUsername(username);

        if (username.isEmpty()) {
            throw new UsernameNotFoundException("unauthorized");
        }

        var user = userOptional.get();

        var userAuthorities = userAuthorityRepository.findAllByUserId(user.getId());
        var grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
        for (var userAuthority : userAuthorities) {
            var authorityOptional = authorityRepository.findById(userAuthority.getAuthorityId());
            if (authorityOptional.isPresent()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authorityOptional.get().getAuthority()));
            }

        }

        var userDetails = new UserDetailsImplementation();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setIsAccountNonExpired(user.getIsAccountNonExpired());
        userDetails.setIsAccountNonLocked(user.getIsAccountNonLocked());
        userDetails.setIsEnabled(user.getIsEnabled());
        userDetails.setIsCredentialsNonExpired(user.getIsCredentialsNonExpired());
        userDetails.setSimpleGrantedAuthorities(grantedAuthorities);

        return userDetails;
    }

    @Override
    public ResponseEntity<?> createUser(String username, String password) {

        var userOptional = applicationUserRepository.findApplicationUserByUsername(username);

        if (userOptional.isPresent()) {

            return response(String.format("user %s already exists!", username));
        }

        var user = new ApplicationUser();
        user.setUsername(username);
        var encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        System.out.printf("username : %s, password : %s%n", username, encodedPassword);
        user.setIsAccountNonExpired(true);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);

        applicationUserRepository.save(user);
        return response("user was successfully created");
    }

}
