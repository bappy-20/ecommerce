package com.inovex.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.OrderHistory;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Long> {

    public List<OrderHistory> findAllByOrderStatus(String orderStatus);

    public List<OrderHistory> findAllByEmployeeId(String employeeId);

    @Query("SELECT l FROM OrderHistory l where l.employeeId = :employeeId" + " AND DATE(createdAt)= CURRENT_DATE")
    public List<OrderHistory> getOrderByCurdate(@Param("employeeId") String employeeId);

    @Query(value = "SELECT a FROM OrderHistory a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate AND "
            + " a.employeeId=:employeeId")
    List<OrderHistory> getAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("employeeId") String employeeId);

    @Query(value = "SELECT a FROM OrderHistory a WHERE " + " a.orderId=:orderId")
    Optional<OrderHistory> findByOrderId(@Param("orderId") String orderId);

    @Query(value = "SELECT a FROM OrderHistory a WHERE " + " a.orderId=:orderId")
    Optional<OrderHistory> findByOrderIdAndStatus(@Param("orderId") String orderId);

    @Query("select e from OrderHistory e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) and e.employeeId=:employeeId and orderStatus='Pending'")
    List<OrderHistory> findAllOfCurrentMonth(@Param("employeeId") String employeeId);

    @Query("select e.employeeId,sum(e.grandTotal) from OrderHistory e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) "
            + "and (e.orderStatus='Pending' or orderStatus='Ready-To-Delivery') and e.orgId=:orgId group by employeeId order by sum(e.grandTotal) DESC")
    List<Object> findAllOfCurrentMonthforGrading(@Param("orgId") long orgId);

    @Query("select e from OrderHistory e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date)")
    List<OrderHistory> findTotalMonthOrder();

    @Query("SELECT l FROM OrderHistory l where  DATE(createdAt)= CURRENT_DATE")
    public List<OrderHistory> getTotalOrderByCurdate();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Pending' AND DATE(createdAt)= CURRENT_DATE ")
    public List<OrderHistory> getPendingOrderByCurdate();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Ready-To-Delivery' AND DATE(createdAt)= CURRENT_DATE")
    public List<OrderHistory> getReadyToDeliveryOrderByCurdate();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Shipped' AND  DATE(createdAt)= CURRENT_DATE")
    public List<OrderHistory> getShippedOrderByCurdate();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Delivered' " + " AND DATE(createdAt)= CURRENT_DATE")
    public List<OrderHistory> getDeliveredOrderByCurdate();

    @Query("select e from OrderHistory e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) and e.orderStatus='Delivered'")
    List<OrderHistory> findDeliveredCurrentMonth();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Cancelled' " + " AND DATE(updated_at)= CURRENT_DATE")
    public List<OrderHistory> getCancelledOrderByCurdate();

    @Query("select e from OrderHistory e where year(e.createdAt) = year(current_date) and "
            + " month(e.createdAt) = month(current_date) and e.orderStatus='Cancelled'")
    List<OrderHistory> findCancelledCurrentMonth();

    @Query("SELECT l FROM OrderHistory l where l.orderStatus='Shipped' AND l.deliveryMan = :employeeId AND l.marketId = :marketId")
    public List<OrderHistory> getShippedByEmp(@Param("employeeId") String employeeId, @Param("marketId") long marketId);

    @Query("SELECT l FROM OrderHistory l where l.deliveryMan = :employeeId"
            + " AND year(l.deliveryDate) = year(current_date)"
            + " and month(l.deliveryDate) = month(current_date) and l.orderStatus='Delivered'")
    public List<OrderHistory> getDeliveredOrderByEmployee(@Param("employeeId") String employeeId);

    @Query(value = "SELECT a FROM OrderHistory a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate AND "
            + " a.orderStatus=:status")
    List<OrderHistory> findAllByDateAndStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("status") String status);

    @Query(value = "SELECT a FROM OrderHistory a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate AND "
            + " a.orderStatus=:ordetType and a.retailId=:retailId")
    public List<OrderHistory> findRetailWiseByDateAndStatus(@Param("startDate") Date startDate,
            @Param("endDate") Date endDate, @Param("retailId") long retailId, @Param("ordetType") String ordetType);

    @Query(value = "SELECT a FROM OrderHistory a WHERE DATE(a.createdAt) >= :startDate AND DATE(a.createdAt) <= :endDate AND "
            + " a.retailId=:retailId")
    public List<OrderHistory> findRetailWiseByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("retailId") long retailId);

    @Query(value = "select sum(grand_total) from order_history where order_status='Shipped' or order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery'", nativeQuery = true)
    Optional<String> getTotalSale();

    @Query(value = "select sum(grand_total) from order_history where ( order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery') and  cast(created_at as date)=curdate()", nativeQuery = true)
    Optional<String> getTotalSaleToday();

    @Query(value = "select sum(grand_total) from order_history where order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery' and month(cast(created_at as date))=month(curdate()) and year(cast(created_at as date))=year(curdate())", nativeQuery = true)
    Optional<String> getTotalSaleMonth();

    @Query(value = "select sum(due_amount) from order_history  where order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery'", nativeQuery = true)
    Optional<String> getTotalDue();

    @Query(value = "select sum(due_amount) from order_history  where order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery' and  cast(created_at as date)=curdate()", nativeQuery = true)
    Optional<String> getTotalDueToday();

    @Query(value = "select sum(due_amount) from order_history  where order_status='Shipped' or order_status='Delivered' or order_status='Ready-To-Delivery' and month(cast(created_at as date))=month(curdate()) and year(cast(created_at as date))=year(curdate())", nativeQuery = true)
    Optional<String> getTotalDueMonth();

    @Query(value = "SELECT cast(created_at as date),sum(grand_total) "
            + " FROM order_history where order_status='Delivered' and month(cast(created_at as date))=month(curdate()) and year(cast(created_at as date))=year(curdate())"
            + " group by cast(created_at as date) order by cast(created_at as date) ASC", nativeQuery = true)
    public List<Object> getSaleByDate();

    @Query(value = "SELECT retail_id,retail_name,market_id,market_name FROM order_history WHERE payment_status='Non-Paid' and org_id=?1 group by retail_id", nativeQuery = true)
    public List<Object> findNonPaidRetail(long orgId);

    @Query(value = "SELECT order_id,total,discount,grand_total,date_receive,due_amount FROM order_history WHERE payment_status='Non-Paid' and retail_id=?1", nativeQuery = true)
    public List<Object> findNonPaidOrderByRetail(String retailId);

    @Transactional
    @Modifying
    @Query(value = "delete from order_history_order_details where order_history_order_details.order_history_id = ?1", nativeQuery = true)
    public int deleteFromOrderDetails(long orderId);

    @Transactional
    @Modifying
    @Query(value = "delete from order_history_processed_order_history where order_history_processed_order_history.order_history_id = ?1", nativeQuery = true)
    public int deleteFromProcessedOrderDetails(long orderId);

    @Query(value = "SELECT * FROM order_history where retail_id = ?1", nativeQuery = true)
    public List<OrderHistory> findAllOrderHistorybyReatilId(long retailId);

//    @Query(value = "SELECT * FROM order_history where order_id = ?1", nativeQuery = true)
//    Optional<OrderHistory> findOrderHistoryByOrderId(String orderId);

}
