package com.inovex.main.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.inovex.main.service.SecurityService;

/**
 * SecurityServiceImpl class
 *
 * @author rabiul
 *
 */
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        logger.info("SecurityServiceImpl: findLoggedInUsername start");
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.info("SecurityServiceImpl: findLoggedInUsername end");
        if (userDetails instanceof UserDetails && !(StringUtils.isEmpty(userDetails))) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    @Override
    public boolean autologin(String username, String password) {
        logger.info("SecurityServiceImpl: autologin start");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
            return true;
        }
        logger.info("SecurityServiceImpl: autologin end");
        return false;
    }
}
