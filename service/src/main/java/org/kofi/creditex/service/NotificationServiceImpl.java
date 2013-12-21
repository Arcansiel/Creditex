package org.kofi.creditex.service;

import org.kofi.creditex.model.Notification;
import org.kofi.creditex.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public List<Notification> GetNotificationsByClientIdAndViewed(long clientId, boolean viewed) {
        return notificationRepository.findByClientIdAndViewed(clientId, viewed);
    }

    @Override
    public Notification GetLatestNotViewedNotificationByClientId(long clientId) {
        List<Notification> notifications = notificationRepository.findByClientIdAndViewed(clientId, false, new PageRequest(0,1, Sort.Direction.DESC, "notificationDate"));
        if (notifications.size()>0){
            return notifications.get(0);
        }
        return null;
    }
}
