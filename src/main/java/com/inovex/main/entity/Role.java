package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
