package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.hibernate.entity.User;

/**
 * This is implementation of spring security user service
 * 
 * @author Mayank Sharma
 *
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = null;
        try {
            user = userService.findByUsername(username);
        } catch (Exception exception) {
            logger.error("Exception in finding user with username: " + username);
            logger.error("Exception: " + exception);
            return null;
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        if (user == null) {
            logger.debug("User not found with username: " + username);
            throw new UsernameNotFoundException("Username not found");
        }
        logger.debug("User is found with username: "+username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, getGrantedAuthorities(user));
    }

    /**
     * This method check for user role and set authority
     * 
     * @param user
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getUserRole()));
        return authorities;
    }

}
