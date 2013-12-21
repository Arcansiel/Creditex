package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/account/prior")
public class AccountPriorController {
    @RequestMapping("/list")
    public String ListPrior(){

    }

    @RequestMapping("/{id}/view")
    public String ViewPrior(){

    }
}
