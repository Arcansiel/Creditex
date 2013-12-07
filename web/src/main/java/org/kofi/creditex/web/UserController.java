package org.kofi.creditex.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.put("data", user.getUserData());

        return "change_user_data";
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

        UserData userData = new UserData();
        userData
                .setFirst(form.getFirst())
                .setLast(form.getLast())
                .setPatronymic(form.getPatronymic())
                .setPassportSeries(form.getSeries())
                .setPassportNumber(form.getNumber())
                .setWorkName(form.getWorkName())
                .setWorkPosition(form.getWorkPosition())
                .setWorkIncome(form.getWorkIncome());
        Authority authority = userService.GetAuthorityByAuthorityName(form.getRole());
        User user = new User();
        user
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setCredentialsNonExpired(true)
                .setEnabled(true)
                .setUsername(form.getUsername())
                .setPassword(userService.GetHashedPassword(form.getPassword(),form.getUsername()))
                .setUserData(userData)
                .setAuthority(authority);
        userService.SaveUser(user);

        return "redirect:/";
    }
}
