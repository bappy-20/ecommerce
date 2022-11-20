package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expense")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Expense extends BaseEntity implements Serializable {

    @Column(name = "expense_type")
    private String expenseType;

    @Column(name = "amount")
    private String amount;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "expense_by")
    private String expenseBy;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "status")
    private long status;

    @Column(name = "approved_amount")
    private String approvedAmount;

    @Column(name = "attachment")
    private String attachment;

    private Long orgId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expenseDate;
    

}
