package org.kofi.creditex.web;

import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.Notification;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.NotificationService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Secured("ROLE_CLIENT")
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String MainClientController(Principal principal, ModelMap model){
        User client = userService.GetUserByUsername(principal.getName());
        Credit credit = creditService.findByUsernameAndRunning(principal.getName(), true);
        model.put("credit", credit);
        Notification notification = notificationService.GetLatestNotViewedNotificationByClientId(client.getId());
        model.put("notification", notification);
        return "bank_client";
    }
}
