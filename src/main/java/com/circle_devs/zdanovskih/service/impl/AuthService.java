package com.circle_devs.zdanovskih.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * interface for AuthService
 */
public interface AuthService extends UserDetailsService {
    /**
     * Load user by name in DB
     * @param username name of the user
     * @return UserDetails
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
