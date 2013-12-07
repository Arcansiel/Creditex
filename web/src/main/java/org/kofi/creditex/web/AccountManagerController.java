package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_ACCOUNT_MANAGER")
public class AccountManagerController {
    @RequestMapping("/account_manager/")
    public String MainAccountManager(){
        return "account_manager";
    }
}
