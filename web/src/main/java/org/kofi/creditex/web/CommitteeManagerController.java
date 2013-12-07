package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_COMMITTEE_MANAGER")
public class CommitteeManagerController {
    @RequestMapping("/committee_manager/")
    public String MainCommitteeManager(){
        return "committee_manager";
    }
}
