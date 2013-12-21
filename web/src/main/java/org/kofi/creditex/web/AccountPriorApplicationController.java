package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/account/prior/application")
public class AccountPriorApplicationController {
    @RequestMapping("/")
    public String CreateApplication(){

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String CreateApplicationProcess(){

    }

    @RequestMapping("/list")
    public String ListApplications(){

    }

    @RequestMapping("/{id}/view")
    public String ViewApplication(@PathVariable long id){

    }

    @RequestMapping("/{id}/abort")
    public String AbortApplication(@PathVariable long id){

    }

    @RequestMapping("/{id}/process")
    public String ProcessApplication(@PathVariable long id){

    }

    @RequestMapping("/{id}/register")
    public String RegisterApplication(@PathVariable long id){

    }
}
