package com.inovex.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Menu;

public interface MenuService {

    Optional<Menu> findById(Long id);

    Optional<Menu> findByRoleName(String roleName);

    List<Menu> findAll();

    void deleteById(Long id, HttpServletRequest request);

    Menu update(Menu menu, Long id, HttpServletRequest request);

    Menu save(Menu area, HttpServletRequest request);

    List<Boolean> getUserRight(Set<Menu> menu, HttpServletRequest request, String rolemanagement);
}
