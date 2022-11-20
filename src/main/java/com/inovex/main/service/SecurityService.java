package com.inovex.main.service;

/**
 * SecurityService interface
 *
 * @author rabiul
 *
 */

public interface SecurityService {

    String findLoggedInUsername();

    boolean autologin(String username, String password);
}
