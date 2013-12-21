package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.NotificationService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/client/notification")
public class ClientNotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @RequestMapping("/list")
    public String ListNotifications(Principal principal,@RequestParam boolean viewed, ModelMap model){
        User client = userService.GetUserByUsername(principal.getName());
        model.put("notifications",notificationService.GetNotificationsByClientIdAndViewed(client.getId(), viewed));
        return "bank_client_view_notifications";
    }

    @RequestMapping("/{id}/view")
    public String ShowNotification(@PathVariable long id, ModelMap model){
        model.put("notification", notificationService.GetNotificationById(id));
        notificationService.DiscardNotification(id);
        return "bank_client_view_notification";
    }
}
