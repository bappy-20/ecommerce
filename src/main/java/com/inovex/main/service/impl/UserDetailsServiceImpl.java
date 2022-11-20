package com.inovex.main.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inovex.main.entity.Role;
import com.inovex.main.entity.User;
import com.inovex.main.repository.UserRepository;

/**
 * UserDetailsServiceImpl class
 *
 * @author rabiul
 *
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("UserDetailsServiceImpl: loadUserByUsername start");
        Optional<User> user = userRepository.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.get().getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        logger.info("UserDetailsServiceImpl: loadUserByUsername end");
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), grantedAuthorities);
    }

    public UserDetails loadUserByUserId(long id) throws UsernameNotFoundException {
        logger.info("UserDetailsServiceImpl: loadUserByUsername start");
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        logger.info("UserDetailsServiceImpl: loadUserByUsername end");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                grantedAuthorities);
    }
}
