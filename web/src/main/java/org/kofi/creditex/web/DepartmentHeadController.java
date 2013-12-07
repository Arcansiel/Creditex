package org.kofi.creditex.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_DEPARTMENT_HEAD")
public class DepartmentHeadController {
    @RequestMapping("/department_head/")
    public String MainDepartmentHead(){
        return "department_head";
    }
}
