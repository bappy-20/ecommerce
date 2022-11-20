package com.inovex.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.OrderHistory;

public interface OrderHisoryService {

    Optional<OrderHistory> findById(Long id);

    List<OrderHistory> findAll();

    List<OrderHistory> saveAll(List<OrderHistory> entities);

    void deleteById(Long id);

    public List<OrderHistory> getOrderByCurdate(String employeeId);

    List<OrderHistory> getAllBetweenDates(Date startDate, Date endDate, String employeeId);

    Optional<OrderHistory> findByOrderId(String orderId);

    List<OrderHistory> findAllOfCurrentMonth(String employeeId);

    List<Object> findAllOfCurrentMonthforGrading(long orgId);

    public List<OrderHistory> findAllByStatus(String status);

    List<OrderHistory> findCancelledCurrentMonth();

    public List<OrderHistory> getCancelledOrderByCurdate();

    List<OrderHistory> findDeliveredCurrentMonth();

    public List<OrderHistory> getDeliveredOrderByCurdate();

    public List<OrderHistory> getShippedOrderByCurdate();

    public List<OrderHistory> getReadyToDeliveryOrderByCurdate();

    public List<OrderHistory> getPendingOrderByCurdate();

    public List<OrderHistory> getTotalOrderByCurdate();

    List<OrderHistory> findTotalMonthOrder();

    public List<OrderHistory> findAllByEmployeeId(String employeeId);

    public List<OrderHistory> getShippedByEmp(String employeeId, long marketId);

    public List<OrderHistory> getDeliveredOrderByEmployee(String employeeId);

    Set<OrderHistory> findAllByDateAndStatus(Date startDate, Date endDate, String status, HttpServletRequest request);

    Optional<OrderHistory> findByOrderIdAndStatus(String orderId);

    List<OrderHistory> findRetailWiseByDateAndStatus(Date stDate, Date enDate, long retailId, String ordetType);

    Optional<String> getTotalDue();

    Optional<String> getTotalSale();

    Optional<String> getTotalDueToday();

    Optional<String> getTotalSaleToday();

    Optional<String> getTotalDueMonth();

    Optional<String> getTotalSaleMonth();

    public List<Object> getSaleByDate();

    List<Object> findNonPaidRetail(long orgId);

    public List<Object> findNonPaidOrderByRetail(String retailId);

    OrderHistory save(OrderHistory entity, long orgId);

    OrderHistory save(OrderHistory entity, HttpServletRequest request);
    
    public List<OrderHistory> findAllOrderHistoryByRetailId(long retailId);
    
   // Optional<OrderHistory> findByOrderId1(String orderId);
    
    
}
