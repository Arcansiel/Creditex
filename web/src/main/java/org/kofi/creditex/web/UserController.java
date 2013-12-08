package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.UserChangeDataForm;
import org.kofi.creditex.web.model.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/change_user_data/")
    public String ChangeUserData(ModelMap model, HttpSession session){
        User user = (User) session.getAttribute("user_to_change_data");
        if(user==null)
            return "redirect:/";
        model.put("data", user.getUserData());

        return "change_user_data";
    }
    @RequestMapping("/change_user_data/process/")
    public String ChangeUserData(HttpSession session,@ModelAttribute UserChangeDataForm form, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            User user = (User) session.getAttribute("user_to_change_data");
            if(user==null)
                return "redirect:/";
            model.put("data", user.getUserData());
            model.put("hasError", "Введено неверное значение в одно из полей формы");
            return "change_user_data";
        }
        UserController.log.warn("Form object:"+form.toString());
        userService.ChangeUserDataByForm(form);
        return "redirect:/";
    }


    @RequestMapping("/register/")
    public String ViewRegisterNewUser(){
        return "registration";
    }
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public String RegisterNewUser(ModelMap model,@ModelAttribute UserRegistrationForm form, BindingResult bindingResult) throws NoSuchAlgorithmException {
        String errorName="isError";
        String errorBinding = "Введено недопустимое значение в одно из числовых полей";
        String errorPassword = "Значения паролей не совпадают";
        String errorExistingUser = "Пользователь с таким именем уже существует";

        if (bindingResult.hasErrors()){
            model.put(errorName,errorBinding);
            return "registration";
        }
        if (!form.getPassword().equals(form.getRepeatPassword())){
            model.put(errorName, errorPassword);
            return "registration";
        }
        if(userService.GetUserByUsername(form.getUsername())!=null){
            model.put(errorName, errorExistingUser);
            return "registration";
        }
        userService.RegisterUserByForm(form);

        return "redirect:/";
    }
}
