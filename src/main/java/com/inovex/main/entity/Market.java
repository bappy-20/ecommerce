package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
public class Market extends BaseEntity implements Serializable {

    private String marketName;

    private String address;

    private long territoryId;

    @OneToMany(targetEntity = Retail.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Retail> retails = new HashSet<>();

    @ManyToMany(mappedBy = "mkts")
    private Set<User> users;

    public void setCustomUser(Set<User> aSet) {
        this.users.clear();
        if (aSet != null) {
            this.users.addAll(aSet);
        }
    }

}
