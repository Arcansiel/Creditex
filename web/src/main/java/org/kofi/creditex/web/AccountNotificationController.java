package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/account/notification")
public class AccountNotificationController {
    @RequestMapping("/list")
    public String ListNotifications(){

    }

    @RequestMapping("/{id}/view")
    public String ViewNotification(@PathVariable long id){

    }

    @RequestMapping("/{id}/discard")
    public String DiscardNotification(@PathVariable long id){

    }

}
