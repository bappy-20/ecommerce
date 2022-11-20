package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.SubMenu;
import com.inovex.main.repository.MenuRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepo menuRepol;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Menu> findById(Long id) {
        // TODO Auto-generated method stub
        return menuRepol.findById(id);
    }

    @Override
    public List<Menu> findAll() {
        // TODO Auto-generated method stub
        return menuRepol.findAll();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            menuRepol.deleteFromOrg(orgId, id);
            menuRepol.deleteById(id);
        }

    }

    @Override
    public Menu update(Menu menu, Long id, HttpServletRequest request) {
        Optional<Menu> mn = menuRepol.findById(id);
        Menu m = new Menu();
        if (mn.isPresent()) {
            mn.get().setUpdatedAt(new Date());
            mn.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
            mn.get().setChildren(menu.getSubMenu());
            m = menuRepol.save(mn.get());
        }
        return m;
    }

    @Override
    public Menu save(Menu entity, HttpServletRequest request) {
        Menu mn = new Menu();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<Menu> list = new HashSet<Menu>();
            if (org.isPresent()) {
                list = org.get().getMenu();
                entity.setCreatedAt(new Date());
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity.setActive(true);
                mn = menuRepol.save(entity);
                list.add(mn);
            }
        }
        return mn;
    }

    @Override
    public Optional<Menu> findByRoleName(String roleName) {
        // TODO Auto-generated method stub
        return menuRepol.findByRoleName(roleName);
    }

    @Override
    public List<Boolean> getUserRight(Set<Menu> menu, HttpServletRequest request, String rolemanagement) {
        // TODO Auto-generated method stub
        String role = (String) request.getSession().getAttribute("role");

        List<Boolean> list = new ArrayList<>();
        Optional<Menu> matchingObject = menu.stream().filter(p -> p.getRoleName().equals(role)).findFirst();
        if (matchingObject.isPresent()) {

            Optional<SubMenu> subMenu = matchingObject.get().getSubMenu().stream()
                    .filter(p -> p.getSubMenuName().equals(rolemanagement)).findFirst();
            if (subMenu.isPresent()) {
                boolean canEdit = subMenu.get().isCanEdit();
                boolean canDelete = subMenu.get().isCanDelete();
                list.add(canEdit);
                list.add(canDelete);
            } else {
                list.add(true);
                list.add(true);
            }

        } else {
            list.add(false);
            list.add(false);
        }

        return list;
    }
}
