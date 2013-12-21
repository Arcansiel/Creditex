package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/client/credit")
public class ClientCreditController {
    @Autowired
    private UserService userService;
    @Autowired
    private CreditService creditService;
    @RequestMapping("/list")
    public String ListCredits(@RequestParam boolean running, Principal principal, ModelMap model){
        User client = userService.GetUserByUsername(principal.getName());
        model.put("credits", creditService.GetCreditsByUserIdAndRunning(client.getId(), running));
        return "bank_client_view_credits";
    }

    @RequestMapping("/{id}/view")
    public String ShowCredit(@PathVariable long id, ModelMap model){
        model.put("credit", creditService.GetCreditById(id));
        return "bank_client_view_credit";
    }
}
