package com.inovex.main.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "builder", toBuilder = true)
@AllArgsConstructor
public class CampaignType extends BaseEntity implements Serializable {

	@Column(name = "campaign_type")
	private String campaignType;

	@Column(name = "note")
	private String note;
}
