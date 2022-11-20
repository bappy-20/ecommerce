package com.inovex.main.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_directory", length = 100)
    private String fileDirectory;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension", length = 5)
    private String fileExtension;

    @Column(name = "file_size", length = 100)
    private String fileSize;

    @Column(name = "type", length = 100)
    private String type;

    @Transient
    private String fileBaseName;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column(name = "user_id")
    private String userId;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt = new Date();

    public File() {

    }

    public File(String fileDirectory, String fileName, String fileExtension, String fileBaseName, String userType) {
        super();
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileBaseName = fileBaseName;

    }

    public File(String fileDirectory, String fileName, String fileExtension, String fileSize, String fileBaseName,
            String userId) {
        super();
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileBaseName = fileBaseName;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileBaseName() {
        return fileBaseName;
    }

    public void setFileBaseName(String fileBaseName) {
        this.fileBaseName = fileBaseName;
    }

    public Path getFilePath() {
        if (fileName == null || fileDirectory == null) {
            return null;
        }

        return Paths.get(fileDirectory, fileName);
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}