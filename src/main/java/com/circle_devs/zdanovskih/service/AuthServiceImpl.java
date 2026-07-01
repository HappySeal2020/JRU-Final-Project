package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.service.impl.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.circle_devs.zdanovskih.repository.UserRepository;

/**
 * Service for user authorisation
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userInDb = userRepository.findByLogin(username);
        if (userInDb == null) {
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
        }
        log.info("username: {}, user in db role: {}", username, userInDb.getRole());
        return User.withUsername(userInDb.getLogin())
                .password(userInDb.getPassword())
                .roles(userInDb.getRole().getRole())
                .build();
    }
}
