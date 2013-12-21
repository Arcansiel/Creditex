package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/client/credit")
public class ClientCreditController {
    @RequestMapping("/list")
    public String ListCredits(){
        //TODO
        return "";
    }

    @RequestMapping("/{id}/view")
    public String ShowCredit(@PathVariable long id){
        //TODO
        return "";
    }
}
