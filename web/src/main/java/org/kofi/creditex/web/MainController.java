package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String MainPage(@RequestParam(required = false) Boolean isError, ModelMap model){
        if(isError!=null){
            model.put("isError", "Введены неверные логин или пароль");
            return "main";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
        {
            String authority = ((User) authentication.getPrincipal()).getAuthority().getAuthority();
            if (authority.equals("ROLE_CLIENT"))
                return "redirect:/client/";
            if (authority.equals("ROLE_ACCOUNT_MANAGER"))
                return "redirect:/account/";
            if (authority.equals("ROLE_SECURITY_MANAGER"))
                return "redirect:/security_manager/";
            if (authority.equals("ROLE_COMMITTEE_MANAGER"))
                return "redirect:/committee_manager/";
            if (authority.equals("ROLE_DEPARTMENT_HEAD"))
                return "redirect:/department_head/";
            if (authority.equals("ROLE_OPERATION_MANAGER"))
                return "redirect:/operation_manager/";
        }
        return "main";
    }
}
