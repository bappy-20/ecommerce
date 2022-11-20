package com.inovex.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Password;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.EmployeeResponse;

public interface UserService {

    List<User> findByUserType(String userType);

    User save(User user, HttpServletRequest request) throws Exception;

    List<User> getAllUser();

    User getUser(Long id);

    Optional<User> findUserById(Long id);

    User updateUser(User user, Long id, HttpServletRequest request);

    Long deleteUser(Long id, HttpServletRequest request);

    Optional<User> findByUsername(String username);

    Long findDistBySystemUserId(User user);

    Password updateUserPassword(Password password, HttpServletRequest request);

    User getProfile(User user, HttpServletRequest request);

    public Long getOrganizationByUser(User user);

    void saveUserMapping(List<Long> userId, Long id);

    List<EmployeeResponse> checkUserExistOrNot(String username, String password);

    void resetEmployeePasswordById(String empId, String password);
    
    public ArrayList<Object> getPagination(int start, int length, String searchParam);

    public Long getCountWithSearchParm1();
    
    public ArrayList<Object> getPagination1();
    
    public Long getCountWithSearchParm(String searchParam);
    
    public Optional<User> getUserByFullName(String fullName);
    
    List<Long> findMappedMarketByUserId(long userId);
    
    Long findMappedAreaByUserId(long userId);
    
    Long findMappedRegionByUserId(long userId);
    
    Long findMappedTerritoryByUserId(long userId);
    
    public Long findDistributorNameByUserId(long userId);
    
    List<Long> findterritoriesAssignedWithMarket(long userId);
}
