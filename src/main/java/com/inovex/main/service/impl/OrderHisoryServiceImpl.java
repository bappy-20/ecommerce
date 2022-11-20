package com.inovex.main.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.CollectionModel;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CollectionRepo;
import com.inovex.main.repository.OrderHistoryRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.OrderHisoryService;

@Service
@Transactional
public class OrderHisoryServiceImpl implements OrderHisoryService {

    @Autowired
    OrderHistoryRepo orderRepo;
    @Autowired
    CollectionRepo collectionRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<OrderHistory> findById(Long id) {
        // TODO Auto-generated method stub
        return orderRepo.findById(id);
    }

    @Override
    public List<OrderHistory> findAll() {
        // TODO Auto-generated method stub
        return orderRepo.findAll();
    }

    @Override
    public List<OrderHistory> saveAll(List<OrderHistory> entities) {
        // TODO Auto-generated method stub
        return orderRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        orderRepo.deleteFromOrderDetails(id);
        orderRepo.deleteFromOrderDetails(id);
        orderRepo.deleteById(id);
    }

    @Override
    public List<OrderHistory> getOrderByCurdate(String employeeId) {
        // TODO Auto-generated method stub
        return orderRepo.getOrderByCurdate(employeeId);
    }

    @Override
    public List<OrderHistory> getAllBetweenDates(Date startDate, Date endDate, String employeeId) {
        // TODO Auto-generated method stub
        return orderRepo.getAllBetweenDates(startDate, endDate, employeeId);
    }

    @Override
    public Optional<OrderHistory> findByOrderId(String orderId) {
        // TODO Auto-generated method stub
        return orderRepo.findByOrderId(orderId);
    }

    @Override
    public List<OrderHistory> findAllOfCurrentMonth(String employeeId) {
        // TODO Auto-generated method stub
        return orderRepo.findAllOfCurrentMonth(employeeId);
    }

    @Override
    public List<Object> findAllOfCurrentMonthforGrading(long orgId) {
        // TODO Auto-generated method stub
        return orderRepo.findAllOfCurrentMonthforGrading(orgId);
    }

    @Override
    public List<OrderHistory> findAllByStatus(String status) {
        // TODO Auto-generated method stub
        return orderRepo.findAllByOrderStatus(status);
    }

    @Override
    public List<OrderHistory> findCancelledCurrentMonth() {
        // TODO Auto-generated method stub
        return orderRepo.findCancelledCurrentMonth();
    }

    @Override
    public List<OrderHistory> getCancelledOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getCancelledOrderByCurdate();
    }

    @Override
    public List<OrderHistory> findDeliveredCurrentMonth() {
        // TODO Auto-generated method stub
        return orderRepo.findDeliveredCurrentMonth();
    }

    @Override
    public List<OrderHistory> getDeliveredOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getDeliveredOrderByCurdate();
    }

    @Override
    public List<OrderHistory> getShippedOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getShippedOrderByCurdate();
    }

    @Override
    public List<OrderHistory> getReadyToDeliveryOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getReadyToDeliveryOrderByCurdate();
    }

    @Override
    public List<OrderHistory> getPendingOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getPendingOrderByCurdate();
    }

    @Override
    public List<OrderHistory> getTotalOrderByCurdate() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalOrderByCurdate();
    }

    @Override
    public List<OrderHistory> findTotalMonthOrder() {
        // TODO Auto-generated method stub
        return orderRepo.findTotalMonthOrder();
    }

    @Override
    public List<OrderHistory> findAllByEmployeeId(String employeeId) {
        // TODO Auto-generated method stub
        return orderRepo.findAllByEmployeeId(employeeId);
    }

    @Override
    public List<OrderHistory> getShippedByEmp(String employeeId, long marketId) {
        // TODO Auto-generated method stub
        return orderRepo.getShippedByEmp(employeeId, marketId);
    }

    @Override
    public List<OrderHistory> getDeliveredOrderByEmployee(String employeeId) {
        // TODO Auto-generated method stub
        return orderRepo.getDeliveredOrderByEmployee(employeeId);
    }

    @Override
    public Set<OrderHistory> findAllByDateAndStatus(Date startDate, Date endDate, String status,
            HttpServletRequest request) {
        Set<OrderHistory> list = new HashSet<OrderHistory>();
        if (request.getSession().getAttribute("orgId") != null) {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);

            if (org.isPresent()) {
                list = org.get().getOrderHistory().stream()
                        .filter(p -> df2.format(p.getCreatedAt()).compareTo(df2.format(startDate)) >= 0
                                && df2.format(p.getCreatedAt()).compareTo(df2.format(endDate)) <= 0
                                && p.getOrderStatus().equals(status))
                        .collect(Collectors.toSet());
            }
        }
        // TODO Auto-generated method stub
        // return orderRepo.findAllByDateAndStatus(startDate, endDate, status);
        return list;
    }

    @Override
    public Optional<OrderHistory> findByOrderIdAndStatus(String orderId) {
        // TODO Auto-generated method stub
        return orderRepo.findByOrderIdAndStatus(orderId);
    }

    @Override
    public List<OrderHistory> findRetailWiseByDateAndStatus(Date stDate, Date enDate, long retailId, String orderType) {
        // TODO Auto-generated method stub
        if (orderType.equals("all")) {
            return orderRepo.findRetailWiseByDate(stDate, enDate, retailId);
        } else {
            return orderRepo.findRetailWiseByDateAndStatus(stDate, enDate, retailId, orderType);
        }

    }

    @Override
    public Optional<String> getTotalDue() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalDue();
    }

    @Override
    public Optional<String> getTotalSale() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalSale();
    }

    @Override
    public Optional<String> getTotalDueToday() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalDueToday();
    }

    @Override
    public Optional<String> getTotalSaleToday() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalSaleToday();
    }

    @Override
    public Optional<String> getTotalDueMonth() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalDueMonth();
    }

    @Override
    public Optional<String> getTotalSaleMonth() {
        // TODO Auto-generated method stub
        return orderRepo.getTotalSaleMonth();
    }

    @Override
    public List<Object> getSaleByDate() {
        // TODO Auto-generated method stub
        return orderRepo.getSaleByDate();
    }

    @Override
    public List<Object> findNonPaidRetail(long orgId) {
        // TODO Auto-generated method stub
        return orderRepo.findNonPaidRetail(orgId);
    }

    @Override
    public List<Object> findNonPaidOrderByRetail(String retailId) {
        // TODO Auto-generated method stub
        return orderRepo.findNonPaidOrderByRetail(retailId);
    }

    @Override
    public OrderHistory save(OrderHistory entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        OrderHistory orderHistory = new OrderHistory();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<OrderHistory> list = new HashSet<OrderHistory>();
            if (org.isPresent()) {
                list = org.get().getOrderHistory();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                orderHistory = orderRepo.save(entity);
                list.add(orderHistory);
                org.get().setOrderHistory(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public OrderHistory save(OrderHistory entity, long orgId) {

        OrderHistory orderHistory = new OrderHistory();
        Optional<Organization> org = orgRepo.findById(orgId);
        Set<OrderHistory> list = new HashSet<OrderHistory>();
        if (org.isPresent()) {
            list = org.get().getOrderHistory();
            /*
             * entity.setCreatedAt(new Date()); entity.setActive(true);
             * entity.setUpdatedAt(new Date()); entity.setOrgId(orgId);
             */
            if (entity.getAdvancedPayment() > 0) {
                CollectionModel cl = new CollectionModel();
                cl.setOrderId(entity.getOrderId());
                cl.setTotal(entity.getTotal());
                long due = entity.getGrandTotal() - entity.getAdvancedPayment();
                cl.setDueAmount(due);
                cl.setCollectionDate(new Date());
                cl.setRecieveAmount(entity.getAdvancedPayment());
                cl.setCreatedAt(new Date());
                cl.setUpdatedAt(new Date());
                cl.setCreatedBy(1);
                cl.setActive(true);
                collectionRepo.save(cl);
                if (due > 0) {
                    entity.setDueAmount(due);
                    entity.setPaymentStatus("Non-Paid");
                } else {
                    entity.setDueAmount(due);
                    entity.setPaymentStatus("Paid");
                }
            } else {
                entity.setDueAmount(entity.getGrandTotal());
                entity.setPaymentStatus("Non-Paid");
            }
            entity.setOrderStatus("Pending");
            entity.setActive(true);
            entity.setCreatedAt(new Date());
            entity.setUpdatedAt(new Date());
            entity.setDateReceive(new Date());
            orderHistory = orderRepo.save(entity);
            list.add(orderHistory);
            org.get().setOrderHistory(list);
            orgRepo.save(org.get());
        }
        return orderRepo.save(entity);
    }

    @Override
    public List<OrderHistory> findAllOrderHistoryByRetailId(long retailId) {
        // TODO Auto-generated method stub
        return orderRepo.findAllOrderHistorybyReatilId(retailId);
    }

//	@Override
//	public Optional<OrderHistory> findByOrderId1(String orderId) {
//		// TODO Auto-generated method stub
//		return orderRepo.findOrderHistoryByOrderId(orderId);
//	}

}
