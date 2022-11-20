package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor

public class OrderEcommerceModel extends BaseEntity implements Serializable {
	
		private Long buiyerId;
	
		private String orderNumber;

	    private String status;

	    private Long productWiseOfferId;

	    private String cashBackId;

	    private String totalPrice;

	    private String netDiscount;

	    private String grandTotal;
	    
	    @OneToMany(targetEntity = OrderedProductEcommerce.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	    private Set<OrderedProductEcommerce> orderedProducts = new HashSet<>();
	    
	    public void setChildren(Set<OrderedProductEcommerce> aSet) {

	        this.orderedProducts.clear();
	        if (aSet != null) {
	            this.orderedProducts.addAll(aSet);
	        }
	    }





}
