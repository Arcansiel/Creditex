package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Slf4j
public class MainController {
    @RequestMapping("/")
    public String MainPage(){
        MainController.log.info("Entering main controller");
        return "main";
    }
}
