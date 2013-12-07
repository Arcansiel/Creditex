package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_CLIENT")
public class ClientController {
    @RequestMapping("/client/")
    public String MainClientController(){
        return "bank_client";
    }
}
