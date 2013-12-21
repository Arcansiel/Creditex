package org.kofi.creditex.service;

import org.kofi.creditex.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> GetNotificationsByClientIdAndViewed(long clientId, boolean viewed);
    Notification GetLatestNotViewedNotificationByClientId(long clientId);
    Notification GetNotificationById(long id);
    void DiscardNotification(long id);
}
