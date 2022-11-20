package com.inovex.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.inovex.main.file.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class WorkforceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkforceApplication.class, args);
    }

    /*
     * @PostConstruct public void init() { // Setting Spring Boot SetTimeZone
     * TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dhaka")); }
     */
}
