package org.kofi.creditex.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UserController {
    @RequestMapping("/change_user_data/")
    public String ChangeUserData(){
        return "change_user_data";
    }
}
