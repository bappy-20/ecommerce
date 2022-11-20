package com.inovex.main.json.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistoryResponse {
    private long id;

    private String orderId;

    private long retailId;

    private String retailName;

    private String retailAddress;

    private long distributorId;

    private String distributorName;

    private long marketId;

    private String marketName;

    private String contactPhone;

    private long total;

    private long discount;

    private long grandTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    private String employeeId;

    private String paymentMethod;

    private long advancedPayment;

    private long dueAmount;

    private long collectedAmount;

    private String orderStatus;

    private String deliveryMan;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateReceive;

    private String paymentStatus;
    private boolean canDelete;
    private boolean canEdit;

}
