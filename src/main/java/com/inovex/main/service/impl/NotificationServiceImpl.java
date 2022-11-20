package com.inovex.main.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Notification;
import com.inovex.main.entity.Organization;
import com.inovex.main.file.File;
import com.inovex.main.file.FileDao;
import com.inovex.main.repository.NotificationRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    private FileDao fileDao;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Notification> findById(Long id) {
        // TODO Auto-generated method stub
        return notificationRepo.findById(id);
    }

    @Override
    public List<Notification> findAll() {
        // TODO Auto-generated method stub
        return notificationRepo.findAll();
    }

    @Override
    public Notification getNotification(Long id) {
        Optional<Notification> notification = notificationRepo.findById(id);
        if (notification.isPresent())
            return notification.get();
        throw new NotFoundException();
    }

    @Override
    public List<Notification> saveAll(List<Notification> entities) {
        // TODO Auto-generated method stub
        return notificationRepo.saveAll(entities);
    }

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("orgId")!= null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				notificationRepo.deleteFromOrg(orgid, id);
				notificationRepo.deleteById(id);
			} else {

			}
		}
		
	}

    @Override
    public Notification update(Notification notification, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Optional<Notification> ntf = notificationRepo.findById(id);
        if (ntf.isPresent()) {
            ntf.get().setNotificationType(notification.getNotificationType());
            ntf.get().setTitle(notification.getTitle());
            ntf.get().setMessage(notification.getMessage());
            ntf.get().setFileName(notification.getFileName());
            ntf.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            ntf.get().setUpdatedAt(new Date());
            return notificationRepo.save(ntf.get());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public String sendMessageWithFile(String notificationId, String token) {
        // TODO Auto-generated method stub
        Optional<Notification> noti = notificationRepo.findById(Long.parseLong(notificationId));
        String result = null;
        if (noti.isPresent()) {
            try {
                JSONObject main = new JSONObject();
                JSONObject sub = new JSONObject();
                /*
                 * if (noti.get().getNotificationType().equals("notice")) { sub.put("file_url",
                 * baseurl + "/api/downloadFile/" + noti.get().getFileName()); String image =
                 * null; sub.put("image_url", image);
                 * 
                 * } else { String file = null; sub.put("file_url", file); sub.put("image_url",
                 * baseurl + "/api/downloadFile/" + noti.get().getFileName()); }
                 */
                if (!noti.get().getFileName().equals(null) && !noti.get().getFileName().equals("")) {
                    sub.put("file_url", baseurl + "/api/downloadFile/" + noti.get().getFileName());
                } else {
                    String image = null;
                    sub.put("file_url", image);
                }
                sub.put("notice_title", noti.get().getTitle());
                sub.put("notice_description", noti.get().getMessage());
                sub.put("notice_type", noti.get().getNotificationType());
                Optional<File> findOne = fileDao.findByFileName(noti.get().getFileName());
                if (findOne.isPresent()) {
                    sub.put("file_type", findOne.get().getFileExtension());

                    if (!findOne.get().getFileSize().equals(null) || !findOne.get().getFileSize().equals("")) {
                        double size_bytes = Double.parseDouble(findOne.get().getFileSize());
                        String size = convertBytes(size_bytes);
                        sub.put("file_size", size);
                    } else {
                        sub.put("file_size", "unkwon");
                    }

                } else {
                    sub.put("file_type", "unkwon");
                    sub.put("file_size", "unkwon");
                }
                sub.put("priority", "");
                sub.put("date", System.currentTimeMillis());
                main.put("to", token);
                main.put("data", sub);
                System.out.println(" main " + main.toString());
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection myURLConnection = (HttpURLConnection) url.openConnection();
                myURLConnection.setRequestProperty("Authorization",
                        "key=AAAAiPHLg_s:APA91bGaEwbif1QToyuZIYseiqXZLTgwmSwacpnte1JjWTeDdvbouwXLT5b1b9uCXxA28sKk7P02bW1KhSxgLBaDLQB6mEN9hmam_UFTJ4xkr4qiZkrbSh8hDZuvDO2HgmhLIl_VDYkH");
                myURLConnection.setRequestMethod("POST");
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(myURLConnection.getOutputStream());
                wr.write(main.toString());
                wr.flush();
                BufferedReader rd = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
                result = rd.readLine();
                wr.close();
                rd.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("notification not found with this id : " + notificationId);
        }
        // System.out.println(" result " + result);
        return result;
    }

    private String convertBytes(double bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;
        DecimalFormat df2 = new DecimalFormat("#.##");
        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return df2.format((bytes / kilobyte)) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return df2.format((bytes / megabyte)) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return df2.format((bytes / gigabyte)) + " GB";

        } else if (bytes >= terabyte) {
            return df2.format((bytes / terabyte)) + " TB";

        } else {
            return bytes + " Bytes";
        }
    }

	@Override
	public Notification save(Notification entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Notification notification = new Notification();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<Notification> list = new HashSet<Notification>();		
			if (org.isPresent()) {
				list = org.get().getNotifications();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				notification = notificationRepo.save(entity);
				list.add(notification);
				org.get().setNotifications(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
}