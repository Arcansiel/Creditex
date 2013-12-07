package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_OPERATION_MANAGER")
public class OperationManagerController {
    @RequestMapping("/operation_manager/")
    public String MainOperationManager(){
        return "operation_manager";
    }
}
