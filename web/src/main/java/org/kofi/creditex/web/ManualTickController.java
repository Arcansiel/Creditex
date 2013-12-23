package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class ManualTickController {
    @Autowired
    private CreditexDateProvider dateProvider;

    @RequestMapping(value = {"/tick/","/tick"}, method = RequestMethod.GET)
    public String GetTickPage(Model model){
        model.addAttribute("date",dateProvider.getCurrentSqlDate());
        return "tick";
    }

    @RequestMapping(value = {"/tick/do/","/tick/do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String DoTick(){
        dateProvider.IncreaseDate();
        return "redirect:/tick/";
    }
}
