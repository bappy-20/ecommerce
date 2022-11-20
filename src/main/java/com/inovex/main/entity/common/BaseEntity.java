package com.inovex.main.entity.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BaseEntity class
 *
 * @author Faruk Hossain
 *
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    /**
     * Downloaded jar from https://projectlombok.org/download or use the jar which
     * is downloaded from your maven build. Execute command in terminal: java -jar
     * lombok.jar This command will open window as show in the picture below,
     * install and quit the installer and restart eclipse.
     * https://www.journaldev.com/18124/java-project-lombok
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date updatedAt;

    @Column(name = "created_by")
    private long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
