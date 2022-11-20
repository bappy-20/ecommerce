package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Role;
import com.inovex.main.entity.SubMenu;
import com.inovex.main.entity.User;
import com.inovex.main.repository.MenuRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RoleRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.OrganizationService;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository organizationRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    MenuRepo mnRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepo.findAll();
    }

    @Override
    public Organization getOrganization(Long id) {
        Optional<Organization> organization = organizationRepo.findById(id);

        if (organization.isPresent())
            return organization.get();
        return null;
    }

    @Override
    public Organization createOrganization(Organization organization) {

        Set<User> userList = new HashSet<User>();
        Set<Menu> mnList = new HashSet<Menu>();
        Set<Role> roleList = new HashSet<Role>();
        Role role = new Role();
        role.setCreatedAt(new Date());
        role.setActive(true);
        role.setCreatedBy(1);
        role.setName("Admin");
        Role role1 = roleRepo.save(role);
        Menu mn = new Menu();
        mn.setRoleName(role1.getName());
        SubMenu sb = new SubMenu();
        sb.setSubMenuName("rolemanagement");
        sb.setCanCreate(true);
        sb.setCanDelete(true);
        sb.setCanEdit(true);
        sb.setCanRead(true);
        Set<SubMenu> sbList = new HashSet<SubMenu>();
        sbList.add(sb);
        mn.setSubMenu(sbList);
        Menu mn1 = mnRepo.save(mn);
        mnList.add(mn1);
        String orgLettter = organization.getOrgName().substring(0, 3);
        User user = new User();
        user.setUsername(orgLettter + "_admin");
        user.setUserType(role1.getName());
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        user.setRoles(new HashSet<>(roleRepo.findRoleByName(role1.getName())));
        user.setActive(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setCreatedBy(1);
        User user1 = userRepository.save(user);
        userList.add(user1);

        roleList.add(role1);
        organization.setUpdatedAt(new Date());
        organization.setMenu(mnList);
        organization.setRole(roleList);
        organization.setUsers(userList);
        Organization organization1 = organizationRepo.save(organization);
        return organization1;
    }

    @Override
    public Organization updateOrganization(Organization organization, Long id) {
        if (!id.equals(organization.getId())) {

            throw new NotFoundException("Organization not found");
        }

        Organization organizationUpdate = organizationRepo.getOne(id);
        organizationUpdate.setOrgName(organization.getOrgName());
        organizationUpdate.setOwnerName(organization.getOwnerName());
        organizationUpdate.setContactName(organization.getContactName());
        organizationUpdate.setMobile(organization.getMobile());
        organizationUpdate.setAddress(organization.getAddress());
        organizationUpdate.setEmail(organization.getEmail());
        organizationUpdate.setAbout(organization.getAbout());
        organizationUpdate.setSubscriptionNumber(organization.getSubscriptionNumber());
        organizationUpdate.setSubscriptionPeriod(organization.getSubscriptionPeriod());
        organizationUpdate.setWebsite(organization.getWebsite());
        organizationUpdate.setTransportType(organization.getTransportType());
        organizationUpdate.setAbout(organization.getAbout());
        organizationUpdate.setLogo(organization.getLogo());
        organizationUpdate.setUpdatedAt(new Date());
        organizationRepo.save(organizationUpdate);
        return organizationUpdate;

    }

    @Override
    public Long deleteOrganization(Long id) {

        Optional<Organization> organization = organizationRepo.findById(id);
        if (organization.isPresent()) {
            organizationRepo.delete(organization.get());
            return id;
        }
        throw new NotFoundException("Car not found");
    }

    @Override
    public Organization getOrgProfile(Organization organization, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {

            long id = (long) request.getSession().getAttribute("orgId");

            Organization orgProfile = organizationRepo.getOne(id);

            if (orgProfile.getId() != null)
                return orgProfile;

        }
        return null;
    }

    @Override
    public Optional<Organization> findById(long id) {
        // TODO Auto-generated method stub
        return organizationRepo.findById(id);
    }

    @Override
    public Long getOrganizationByUser(User user) {
        Organization organization = organizationRepo.findOrganizationByUserId(user.getId());
        if (organization != null) {
            return organization.getId();
        }
        return null;
    }

//    @Override
//    public List<Boolean> getUserRight(Set<Menu> menu, HttpServletRequest request, String rolemanagement) {
//        // TODO Auto-generated method stub
//        String role = (String) request.getSession().getAttribute("role");
//        List<Boolean> list = new ArrayList<>();
//        Optional<Menu> matchingObject = menu.stream().filter(p -> p.getRoleName().equals(role)).findFirst();
//        if (matchingObject.isPresent()) {
//            Optional<SubMenu> subMenu = matchingObject.get().getSubMenu().stream()
//                    .filter(p -> p.getSubMenuName().equals(rolemanagement)).findFirst();
//            if (subMenu.isPresent()) {
//                boolean canEdit = subMenu.get().isCanEdit();
//                boolean canDelete = subMenu.get().isCanDelete();
//                list.add(canEdit);
//                list.add(canDelete);
//            } else {
//                list.add(true);
//                list.add(true);
//            }
//
//        } else {
//            list.add(false);
//            list.add(false);
//        }
//
//        return list;
//    }

    @Override
    public List<Boolean> getUserRight(Set<Organization> organaization, HttpServletRequest request,
            String rolemanagement) {
        // TODO Auto-generated method stub
//		 String role = (String) request.getSession().getAttribute("role");
//		 List<Boolean> list = new ArrayList<>();
//		  Optional<Organization> matchingObject = organaization.stream().filter(p -> p.getOrgName().equals(role)).findFirst();
//		 if (matchingObject.isPresent()) {
//			 Optional<SubMenu> subMenu = matchingObject.get().getSubMenu().stream()
//                   .filter(p -> p.getSubMenuName().equals(rolemanagement)).findFirst();
//			 if (subMenu.isPresent()) {
//				 boolean canEdit = subMenu.get().isCanEdit();
//				 boolean canDelete = subMenu.get().isCanDelete();
//				 list.add(canEdit);
//				 list.add(canDelete);
//			} else {
//				 list.add(true);
//                 list.add(true);
//
//			}
//		}
//		  else {
//			 list.add(false);
//             list.add(false);
//		}
        return null;
    }
}
