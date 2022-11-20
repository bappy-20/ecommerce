package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collections")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class CollectionModel extends BaseEntity implements Serializable {

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "total")
    private long total;

    @Column(name = "recieve_amount")
    private long recieveAmount;

    @Column(name = "due_amount")
    private long dueAmount;

    @Column(name = "collection_date", columnDefinition = "DATE")
    private Date collectionDate;
}
