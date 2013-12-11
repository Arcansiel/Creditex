package org.kofi.creditex.web;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.ProductService;
import org.kofi.creditex.web.model.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_DEPARTMENT_HEAD")
public class DepartmentHeadController {

    @Autowired
    ProductService productService;

    @RequestMapping("/department_head/")
    public String MainDepartmentHead(){
        return "department_head";
    }

    @RequestMapping(value = "/department_head/appliances/committee_approved", method = RequestMethod.GET)
    public String DepartmentHead1(Model model){
        return "department_head_committee_approved";
    }

    @RequestMapping(value = "/department_head/appliances/committee_rejected", method = RequestMethod.GET)
    public String DepartmentHead2(Model model){
        return "department_head_committee_rejected";
    }

    @RequestMapping(value = "/department_head/product/list/", method = RequestMethod.GET)
    public String DepartmentHead3(Model model){
        model.addAttribute("activated",productService.GetProductFormsByActive(true));
        model.addAttribute("deactivated",productService.GetProductFormsByActive(false));
        return "department_head_product_list";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.GET)
    public String DepartmentHead4(Model model){
        return "department_head_product_creation";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.POST)
    public String DepartmentHead41(Model model,
                                  @ModelAttribute ProductForm productForm){
        if(productService.CreateProductByForm(productForm) == 0){
            return "redirect:/department_head/?product_created=true";
        }else{
            return "redirect:/department_head/?product_created=false";
        }

    }

    @RequestMapping(value = "/department_head/prolongation/list/", method = RequestMethod.GET)
    public String DepartmentHead5(Model model){
        return "department_head_prolongation_list_view";
    }

    @RequestMapping(value = "/department_head/prolongation/{id}", method = RequestMethod.GET)
    public String DepartmentHead6(Model model,
                                  @PathVariable("id")int id){
        return "department_head_prolongation_view";
    }

    @RequestMapping(value = "/department_head/product/{id}/set_active/{active}", method = {RequestMethod.GET, RequestMethod.POST})
    public String DepartmentHead7(Model model,
                                  @PathVariable("id")int id,
                                  @PathVariable("active")boolean active){
        if(productService.SetProductIsActive(id, active)){
            return "redirect:/department_head/?product_state_changed=true";
        }else{
            return "redirect:/department_head/?product_state_changed=false";
        }
    }

    @RequestMapping(value = "/department_head/appliances/{id}/set_head_approved/{approved}", method = {RequestMethod.GET, RequestMethod.POST})
    public String DepartmentHead8(Model model, Principal principal,
                                  @PathVariable("id")int id,
                                  @PathVariable("approved")boolean approved){
        return "redirect:/department_head/";
    }
    /*
    @RequestMapping(value = "/department_head/9", method = {RequestMethod.GET, RequestMethod.POST})
    public String DepartmentHead9(Model model,
                                  @PathVariable("id")int id){
        return "redirect:/department_head/";
    }

    @RequestMapping(value = "/department_head/10", method = RequestMethod.GET)
    public String DepartmentHead10(Model model){
        return "department_head";
    }

    @RequestMapping(value = "/department_head/11", method = RequestMethod.GET)
    public String DepartmentHead11(Model model){
        return "department_head";
    }

    @RequestMapping(value = "/department_head/12", method = RequestMethod.GET)
    public String DepartmentHead12(Model model){
        return "department_head";
    }
    */
}
