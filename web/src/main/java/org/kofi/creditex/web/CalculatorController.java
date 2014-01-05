package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.*;
import org.kofi.creditex.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class CalculatorController {

    @Autowired
    ProductService productService;

    @Autowired
    CreditexDateProvider dateProvider;

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            switch (error) {
                case "invalid_input_data":
                    model.addAttribute("error", "Введены некорректные данные");
                    if (info != null) {
                        model.addAttribute("info", "Введены некорректные данные в поле " + info);
                    }
                    break;
                case "no_product":
                    model.addAttribute("error", "Кредитный продукт не найден в системе");
                    if(info != null){
                        model.addAttribute("info","ID кредитного продукта : "+info);
                    }
                    break;
            }
        }else if(info != null){
            model.addAttribute("info",info);
        }
    }

    @RequestMapping(value="/credit_calculator/",method = RequestMethod.GET)
    public String Main(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        return "credit_calculator";
    }

    @RequestMapping(value="/credit_calculator/products/",method = RequestMethod.GET)
    public String Products(Model model){
        model.addAttribute("products",productService.GetProductsByActive(true));
        return "credit_calculator_products";
    }

    @RequestMapping(value="/credit_calculator/for_product/{id}",method = RequestMethod.GET)
    public String Form(Model model
        ,@PathVariable("id") long id
        ,@RequestParam(value = "request", required = false)Long request
        ,@RequestParam(value = "duration", required = false)Long duration
    ){
        Product product = productService.GetProductById(id);
        if(product == null){
            return "redirect:/credit_calculator/?error=no_product&info="+id;
        }
        model.addAttribute("product",product);
        if(request != null){ model.addAttribute("request",request); }
        if(duration != null){ model.addAttribute("duration",duration); }
        return "credit_calculator_form";
    }

    @RequestMapping(value="/credit_calculator/calculate/",method = {RequestMethod.GET,RequestMethod.POST})
    public String Calculate(Model model
        ,@Valid @ModelAttribute CreditCalculatorForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/credit_calculator/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        if(form.getDuration() <= 0){
            return "redirect:/credit_calculator/?error=invalid_input_data&info=duration";
        }
        if(form.getRequest() <= 0){
            return "redirect:/credit_calculator/?error=invalid_input_data&info=request";
        }
        Product product = productService.GetProductById(form.getProductId());
        if(product == null){
            return "redirect:/credit_calculator/?error=no_product&info="+form.getProductId();
        }
        long[] result = new long[3];
        List<Payment> payments = CreditCalculator.PaymentPlan(
                product, form.getRequest(), form.getDuration()
                , dateProvider.getCurrentSqlDate(), result);
        model.addAttribute("product",product);
        model.addAttribute("form",form);
        model.addAttribute("payments",payments);
        model.addAttribute("mainDebt",result[0]);
        model.addAttribute("percents",result[1]);
        model.addAttribute("total",result[2]);
        return "credit_calculator_result";
    }

    @RequestMapping(value="/credit_calculator/find_products/",method = RequestMethod.GET)
    public String FindProductsForm(){
        return "credit_calculator_find_products_form";
    }

    @RequestMapping(value="/credit_calculator/find_products/",method = RequestMethod.POST)
    public String FindProductsFormPost(Model model
            ,@Valid @ModelAttribute CreditCalculatorForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/credit_calculator/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        model.addAttribute("products",
                CreditCalculator.RequireProducts(
                productService.GetProductsByActive(true), form.getRequest(), form.getDuration(), Long.MAX_VALUE
                )
        );
        model.addAttribute("request",form.getRequest());
        model.addAttribute("duration",form.getDuration());
        return "credit_calculator_products_found";
    }
}
