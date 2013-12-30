package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
    public String DoTick(
            HttpSession session
            ,@RequestParam(value = "count", required = false, defaultValue = "1") int count
    ){
        if(count < 1){
            return "redirect:/tick/?info=0";
        }
        if(count > 365){
            count = 365;
        }
        if(session.getAttribute("manual_tick_is_running") != null){
            return "redirect:/tick/?info=previous_tick_is_running";
        }
        session.setAttribute("manual_tick_is_running",count);
        for(int i = 0; i < count; i++){
            dateProvider.IncreaseDate();
        }
        session.removeAttribute("manual_tick_is_running");
        return "redirect:/tick/?info="+count;
    }
}
