package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/account/notification")
public class AccountNotificationController {
    @Autowired
    private NotificationService notificationService;
    @RequestMapping("/list")
    public String ListNotifications(@RequestParam boolean viewed,HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account/";
        model.put("notifications",notificationService.GetNotificationsByClientIdAndViewed(client.getId(), viewed));
        return "account_manager_notification_list_view";
    }

    @RequestMapping("/{id}/view")
    public String ViewNotification(@PathVariable long id, ModelMap model){
        model.put("notification", notificationService.GetNotificationById(id));
        return "account_manager_notification_view";
    }

    @RequestMapping("/{id}/discard")
    public String DiscardNotification(@PathVariable long id){
        notificationService.DiscardNotification(id);
        return "redirect:/account";
    }

}
