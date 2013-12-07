package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String MainPage(HttpSession session ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
        {
            String authority = ((User) authentication.getPrincipal()).getAuthority().getAuthority();
            if (authority.equals("ROLE_CLIENT"))
                return "redirect:/client/";
            if (authority.equals("ROLE_ACCOUNT_MANAGER"))
                return "redirect:/account_manager/";
            if (authority.equals("ROLE_SECURITY_MANAGER"))
                return "redirect:/security_manager/";
            if (authority.equals("ROLE_COMMITTEE_MANAGER"))
                return "redirect:/committee_manager/";
            if (authority.equals("ROLE_DEPARTMENT_HEAD"))
                return "redirect:/department_head/";
            if (authority.equals("ROLE_OPERATION_MANAGER"))
                return "redirect:/operation_manager/";
        }

        MainController.log.info("Entering main controller");
        return "main";
    }
    @RequestMapping("/login/")
    public String LoginPage(){
        return "login";
    }
}
