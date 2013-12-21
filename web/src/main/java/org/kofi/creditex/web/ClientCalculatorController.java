package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Product;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.CreditCalculator;
import org.kofi.creditex.service.ProductService;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.kofi.creditex.web.model.CreditApplicationRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/client/calculator")
public class ClientCalculatorController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @RequestMapping("")
    public String Calculator(){
        return "bank_client_select_boundary";
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String CalculatorProcess(Principal principal,@Valid @ModelAttribute CreditApplicationRegistrationForm form, BindingResult result, ModelMap model){
        if(result.hasErrors())
            return "redirect:/client/calculator";
        List<Product> products = productService.GetProductsByActive(true);
        User client = userService.GetUserByUsername(principal.getName());
        List<Product> satisfactoryProducts=CreditCalculator.RequireProducts(products, form.getRequest(), form.getDuration(), client.getUserData().getWorkIncome()/2);
        model.put("products", satisfactoryProducts);
        return "bank_client_view_products";
    }
}
