package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/account/prolongation")
public class AccountProlongationController {
    @RequestMapping("/list")
    public String ListProlongations(){

    }

    @RequestMapping("/{id}/view")
    public String ViewProlongation(@PathVariable long id){

    }
}
