package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_SECURITY_MANAGER")
public class SecurityManagerController {
    @RequestMapping("/security_manager/")
    public String MainSecurityManager(){
        return "security_manager";
    }
}
