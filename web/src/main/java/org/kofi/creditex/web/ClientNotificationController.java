package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/client/notification")
public class ClientNotificationController {
    @RequestMapping("/list")
    public String ListNotifications(){
        //TODO
        return "";
    }

    @RequestMapping("/{id}/view")
    public String ShowNotification(){
        //TODO
        return "";
    }
}
