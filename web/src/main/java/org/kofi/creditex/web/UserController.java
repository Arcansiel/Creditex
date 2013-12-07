package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Slf4j
public class UserController {
    @RequestMapping("/change_user_data/")
    public String ChangeUserData(ModelMap model, HttpSession session){
        User user = (User) session.getAttribute("user_to_change_data");
        model.put("data", user.getUserData());
        log.info(model.toString());
        return "change_user_data";
    }
}
