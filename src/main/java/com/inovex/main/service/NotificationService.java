package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Notification;

public interface NotificationService {
    Optional<Notification> findById(Long id);

    List<Notification> findAll();

    Notification getNotification(Long id);

    List<Notification> saveAll(List<Notification> entities);

    void deleteById(Long id,HttpServletRequest request);

    Notification update(Notification notification, Long id, HttpServletRequest request);

    String sendMessageWithFile(String notificationId, String token);
    
    Notification save(Notification entity, HttpServletRequest request);
}