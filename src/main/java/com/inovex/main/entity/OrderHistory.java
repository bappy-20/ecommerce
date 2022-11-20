package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class OrderHistory extends BaseEntity implements Serializable {

    @Column(name = "order_id", unique = true)
    private String orderId;

    @Column(name = "retail_id")
    private long retailId;

    @Column(name = "retail_name")
    private String retailName;

    @Column(name = "retail_address")
    private String retailAddress;

    @Column(name = "distributor_id")
    private long distributorId;

    @Column(name = "distributor_name")
    private String distributorName;

    @Column(name = "market_id")
    private long marketId;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "total")
    private long total;

    @Column(name = "discount")
    private long discount;

    @Column(name = "grand_total")
    private long grandTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Column(name = "employee_id")
    private String employeeId;

    @OneToMany(targetEntity = OrderDetails.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @OneToMany(targetEntity = ProcessedOrderHistory.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProcessedOrderHistory> processedOrderHistory = new HashSet<>();

    public void setProcessedOrderHistory(Set<ProcessedOrderHistory> aSet) {
        this.processedOrderHistory.clear();
        if (aSet != null) {
            this.processedOrderHistory.addAll(aSet);
        }
    }

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "advanced_payment")
    private long advancedPayment;

    @Column(name = "due_amount")
    private long dueAmount;

    @Column(name = "collected_amount")
    private long collectedAmount;

    private String orderStatus;

    private String deliveryMan;

    @Column(name = "date_receive", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateReceive;

    private String paymentStatus;

    private long orgId;

}
