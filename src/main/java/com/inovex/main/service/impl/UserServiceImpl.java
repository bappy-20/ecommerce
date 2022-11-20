package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
///import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Password;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.EmployeeResponse;
import com.inovex.main.json.response.MarketResponse;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RoleRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.RegionModelService;
import com.inovex.main.service.SecurityService;
import com.inovex.main.service.TerritoryService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.UserTypeService;

/**
 * @author Faruk
 * @author rabiul
 * 
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DistributorRepo disRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    TerritoryService territoryService;
    @Autowired
    RegionModelService regionService;
    @Autowired
    AreaService areaService;

    @Override
    public List<User> findByUserType(String userType) {
        // TODO Auto-generated method stub
        return userRepository.findByUserType(userType);
    }

    // public static final Logger logger =
    // LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
            return user.get();
        return null;
    }

    @Override
    public User updateUser(User user, Long id, HttpServletRequest request) {
        if (!id.equals(user.getId())) {

            throw new NotFoundException("User not found");
        }
        User userUpdate = userRepository.getOne(id);
        userUpdate.setFullName(user.getFullName());
        userUpdate.setUsername(user.getUsername());
        userUpdate.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userUpdate.setUserType(user.getUserType());
        userUpdate.setMobile(user.getMobile());
        userUpdate.setProfileImage(user.getProfileImage());
        userUpdate.setPresentAddress(user.getPresentAddress());
        userUpdate.setNidNumber(user.getNidNumber());
        userUpdate.setBloodGroup(user.getBloodGroup());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setUpdatedAt(new Date());
        userUpdate.setCreatedBy((long) request.getSession().getAttribute("userId"));
        userUpdate.setNidCopy(user.getNidCopy());
        userRepository.save(userUpdate);
        return userUpdate;

    }

    @Override
    public Long deleteUser(Long id, HttpServletRequest request) {

        if (request.getSession().getAttribute("orgId") != null) {

            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteFromDistUser(id);
                userRepository.deleteFromOrg(orgId, id);
                userRepository.deleteUserMarket(id);
                userRepository.deleteUserFromUserAreas(id);
                userRepository.deleteUserFromUserRegions(id);
                userRepository.deleteUserFromUserRoles(id);
                userRepository.deleteUserFromUserTerritories(id);
                userRepository.delete(user.get());
                // userRepository.deleteById(id);
            }
        }
        return id;

    }

    @Override
    public Password updateUserPassword(Password password, HttpServletRequest request) {

        if (request.getSession().getAttribute("userId") != null) {
            long id1 = (long) request.getSession().getAttribute("userId");
            System.out.println("org is not null" + id1);
            User userUpdate = userRepository.getOne(id1);

            if (userUpdate != null) {
                userUpdate.setPassword(bCryptPasswordEncoder.encode(password.getPassword()));
                userRepository.save(userUpdate);

            } else {
                System.out.println("org is null");
            }

        }

        return null;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user, HttpServletRequest request) throws Exception {
        User user1 = new User();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Organization org = orgRepo.getOne(id);
            Set<User> userList = new HashSet<User>();
            if (org != null) {
                userList = org.getUsers();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setRoles(new HashSet<>(roleRepository.findRoleByName(user.getUserType())));
                user.setActive(true);
                user.setOrgId((long) request.getSession().getAttribute("orgId"));
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                user.setCreatedBy((long) request.getSession().getAttribute("userId"));
                user1 = userRepository.save(user);
                userList.add(user1);
                org.setUsers(userList);
                orgRepo.save(org);
            }
        } else {
            System.out.println("orgId is null");
        }
        return user1;
    }

    @Override
    public User getProfile(User user, HttpServletRequest request) {
        if (request.getSession().getAttribute("userId") != null) {

            long id1 = (long) request.getSession().getAttribute("userId");

            User userProfile1 = userRepository.getOne(id1);

            if (userProfile1.getId() != null)
                return userProfile1;

        }
        return null;
    }

    @Override
    public Long findDistBySystemUserId(User user) {

        Distributor dist = disRepository.findDistBySystemUserId(user.getId());
        if (dist != null) {
            return dist.getId();
        }
        return null;

    }

    @Override
    public Optional<User> findUserById(Long id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    @Override
    public Long getOrganizationByUser(User user) {
        Organization organization = orgRepo.findOrganizationByUserId(user.getId());
        if (organization != null) {
            return organization.getId();
        }
        return null;
    }

//  @Override
//  public void saveUserMapping(List<Long> userId, Long id) {
//      Optional<Distributor> dist = disRepository.findById(id);
//      Set<User> userList = new HashSet<>();
//      if (dist.isPresent()) {
//          for (Long long1 : userId) {
//              Optional<User> user = userRepository.findById(long1);
//              if (user.isPresent()) {
//
//                  userList = dist.get().getUsers();
//                  userList.add(user.get());
//                  user.get().setDealerId(dist.get().getId());
//                  //userRepository.save(user.get());
//                  try {
//                	  userRepository.save(user.get());
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//              }
//          
//          
//          try {
//        	  dist.get().setCustomUser(userList);
//        	  disRepository.save(dist.get());		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}  
//          }
//      }
//  }

    @Override
    public void saveUserMapping(List<Long> userId, Long id) {
        Optional<Distributor> dist = disRepository.findById(id);
        Set<User> list = new HashSet<>();
        Set<User> userList = new HashSet<>();
        if (dist.isPresent()) {
            for (Long long1 : userId) {
                Optional<User> user = userRepository.findById(long1);
                if (user.isPresent()) {
                    list.add(user.get());
                    userList = dist.get().getUsers();
                    userList.add(user.get());
                    user.get().setDealerId(dist.get().getId());
                    userRepository.save(user.get());
                }
            }
            dist.get().setCustomUser(list);
            disRepository.save(dist.get());
        }

    }

    @Override
    public List<EmployeeResponse> checkUserExistOrNot(String username, String password) {
        List<EmployeeResponse> res = new ArrayList<>();
        EmployeeResponse empRes = new EmployeeResponse();

        boolean login = securityService.autologin(username, password);
        if (login) {
            Optional<User> emp = userRepository.findByUsername(username);
            if (emp.isPresent()) {
                if (emp.get().getDealerId() != null) {
                    Optional<Distributor> dis = disRepository.findById(emp.get().getDealerId());
                    if (dis.isPresent()) {
                        empRes.setDistributorId(dis.get().getId());
                        empRes.setDistributorMobile(dis.get().getDistributorPhone());
                        empRes.setDistributorName(dis.get().getDistributorName());
                        empRes.setDistributorAddress(dis.get().getDistributorAddress());
                    }
                }

                empRes.setOrgId(emp.get().getOrgId());
                empRes.setEmployeeId(emp.get().getUsername());
                empRes.setEmpName(emp.get().getFullName());
                empRes.setEmpImage(emp.get().getProfileImage());
                empRes.setEmpCategory(emp.get().getUserType());
                empRes.setEmpAddress(emp.get().getPresentAddress());
                empRes.setEmpPhone(emp.get().getMobile());
                empRes.setReportingId("Not found");
                empRes.setReportingName("Not found");
                empRes.setReportingMobile("Not found");

                Set<MarketResponse> mktRes = new HashSet<>();
                Set<Market> mkts = emp.get().getMkts();
                if (mkts.size() > 0) {
                    for (Market market : mkts) {
                        int flag = 0;
                        if (flag == 0) {
                            Optional<TerritoryModel> terr = territoryService.findById(market.getTerritoryId());
                            if (terr.isPresent()) {
                                empRes.setTerritoryName(terr.get().getTerritoryName());
                                Optional<AreaModel> area = areaService.findById(terr.get().getAreaId1());
                                if (area.isPresent()) {
                                    empRes.setAreaName(area.get().getAreaName());
                                    Optional<RegionModel> region = regionService.findById(area.get().getRegionId1());
                                    if (region.isPresent()) {
                                        empRes.setRegionName(region.get().getRegionName());
                                    } else {
                                        empRes.setRegionName("not found");
                                    }
                                } else {
                                    empRes.setAreaName("not found");
                                }
                            } else {
                                empRes.setTerritoryName("not found");
                            }

                        }
                        MarketResponse mr = new MarketResponse();
                        mr.setMarketName(market.getMarketName());
                        mr.setAddress(market.getAddress());
                        mr.setRetails(market.getRetails());
                        mktRes.add(mr);
                        flag++;
                    }
                }

                empRes.setMarkets(mktRes);
                res.add(empRes);

            } else {
                EmployeeResponse res1 = new EmployeeResponse();
                res1.setEmpName("Employee not found!");
                res.add(res1);

            }
        }
        return res;

    }

    @Override
    public void resetEmployeePasswordById(String empId, String password) {
        Optional<User> emp = userRepository.findByUsername(empId);
        if (emp.isPresent()) {
            emp.get().setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(emp.get());
        }

    }

    @Override
    public ArrayList<Object> getPagination(int start, int length, String searchParam) {
        // TODO Auto-generated method stub
        if (searchParam == null || searchParam.isEmpty()) {
            return userRepository.getPagination(start, length);
        } else {
            return userRepository.getPaginationWithSerachParam(searchParam, start, length);
        }
    }

    @Override
    public Long getCountWithSearchParm(String searchParam) {
        // TODO Auto-generated method stub
        if (searchParam == null || searchParam.isEmpty()) {
            return userRepository.count();
        } else {

            return userRepository.countBySearchParam(searchParam);

        }
    }

    @Override
    public Long getCountWithSearchParm1() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Object> getPagination1() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> getUserByFullName(String fullName) {
        // TODO Auto-generated method stub
        return userRepository.findUserByFullName(fullName);
    }

    @Override
    public List<Long> findMappedMarketByUserId(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findAllMarketByUserId(userId);
    }

    @Override
    public Long findMappedAreaByUserId(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findAllAreaByUserId(userId);
    }

    @Override
    public Long findMappedRegionByUserId(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findAllRegionsByUserId(userId);
    }

    @Override
    public Long findMappedTerritoryByUserId(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findAllTerritoryByUserId(userId);
    }

    @Override
    public Long findDistributorNameByUserId(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findDistributorNameByUserId(userId);
    }

    @Override
    public List<Long> findterritoriesAssignedWithMarket(long userId) {
        // TODO Auto-generated method stub
        return userRepository.findAllTerritoriesAssignedWithMarketByUserId(userId);
    }

}
