package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bagration.spring.repository.ApplicationUserRepository;
import ru.bagration.spring.repository.AuthorityRepository;
import ru.bagration.spring.repository.UserAuthorityRepository;
import ru.bagration.spring.security.UserDetailsImplementation;
import ru.bagration.spring.service.ApplicationUserService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImplementation implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        var userOptional = applicationUserRepository.findApplicationUserByUsername(username);

        if (username.isEmpty()) {
            throw new UsernameNotFoundException("unauthorized");
        }

        var user = userOptional.get();

        var userAuthorities = userAuthorityRepository.findAllByUserId(user.getId());
        var grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
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

}
