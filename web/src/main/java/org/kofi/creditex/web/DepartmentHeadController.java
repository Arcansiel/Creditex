package org.kofi.creditex.web;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.DepartmentHeadService;
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

    @Autowired
    DepartmentHeadService departmentHeadService;

    @RequestMapping("/department_head/")
    public String MainDepartmentHead(){
        return "department_head";
    }

    @RequestMapping(value = "/department_head/appliances/committee_approved/", method = RequestMethod.GET)
    public String DepartmentHead1(Model model){
        model.addAttribute("applications",departmentHeadService.GetCommitteeApprovedUncheckedApplications(true));
        return "department_head_committee_approved";
    }

    @RequestMapping(value = "/department_head/appliances/committee_rejected/", method = RequestMethod.GET)
    public String DepartmentHead2(Model model){
        model.addAttribute("applications",departmentHeadService.GetCommitteeApprovedUncheckedApplications(false));
        return "department_head_committee_rejected";
    }

    @RequestMapping(value = "/department_head/appliances/committee_vote/", method = RequestMethod.GET)
    public String DepartmentHeadVoteAppliances(Model model){
        model.addAttribute("applications",departmentHeadService.GetCommitteeVoteApplications());
        return "department_head_committee_vote";
    }

    @RequestMapping(value = "/department_head/appliance/{id}", method = RequestMethod.GET)
    public String DepartmentHeadApplianceView(Model model,
                                  @PathVariable("id")long id){
        Application application = departmentHeadService.GetApplicationById(id);
        if(application != null){
            List<Vote> votes = departmentHeadService.GetApplicationVotes(id);
            model.addAttribute("application",application);
            model.addAttribute("votes",votes);
            return "department_head_appliance_view";
        }else{
            return "redirect:/department_head/?error=no_such_application";
        }

    }

    @RequestMapping(value = "/department_head/appliance/{id}/set_head_approved/", method = RequestMethod.POST)
    public String DepartmentHeadA(Model model, Principal principal,
                                  @PathVariable("id")long id,
                                  @RequestParam("acceptance")boolean acceptance,
                                  @RequestParam("comment")String comment){
        int err;
        if((err = departmentHeadService.SetApplicationHeadApproved(id,principal.getName(),acceptance,comment)) != 0){
            return "redirect:/department_head/?error=code_"+err;
        }
        return "redirect:/department_head/?application_approved="+acceptance;
    }

    @RequestMapping(value = "/department_head/product/list/", method = RequestMethod.GET)
    public String DepartmentHead3(Model model){
        model.addAttribute("activated",productService.GetProductFormsByActive(true));
        model.addAttribute("deactivated",productService.GetProductFormsByActive(false));
        return "department_head_product_list";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.GET)
    public String DepartmentHead4(){
        return "department_head_product_creation";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.POST)
    public String DepartmentHead41(
                                  @ModelAttribute ProductForm productForm){
        if(productService.CreateProductByForm(productForm) == 0){
            return "redirect:/department_head/?product_created=true";
        }else{
            return "redirect:/department_head/?product_created=false";
        }

    }

    @RequestMapping(value = "/department_head/product/{id}/set_active/{active}", method = {RequestMethod.GET, RequestMethod.POST})
    public String DepartmentHead7(
            @PathVariable("id")long id,
            @PathVariable("active")boolean active){
        if(productService.SetProductIsActive(id, active)){
            return "redirect:/department_head/?product_state_changed=true";
        }else{
            return "redirect:/department_head/?product_state_changed=false";
        }
    }

    @RequestMapping(value = "/department_head/prolongation/list/", method = RequestMethod.GET)
    public String DepartmentHead5(Model model){
        model.addAttribute("prolongations",departmentHeadService.GetUncheckedProlongations());
        return "department_head_prolongation_list_view";
    }

    @RequestMapping(value = "/department_head/prolongation/{id}", method = RequestMethod.GET)
    public String DepartmentHead6(Model model,
                                  @PathVariable("id")long id){
        ProlongationApplication prolongation = departmentHeadService.GetProlongation(id);
        if(prolongation != null){
            model.addAttribute("prolongation",prolongation);
            return "department_head_prolongation_view";
        }else{
            return "redirect:/department_head/?error=no_such_prolongation_application";
        }
    }

    @RequestMapping(value = "/department_head/prolongation/{id}/set_head_approved/", method = RequestMethod.POST)
    public String DepartmentHeadProlongationApprove(Model model,
                                  @PathVariable("id")long id,
                                  @RequestParam("confirmation")boolean confirmation){
        int err;
        if((err = departmentHeadService.SetProlongationApproved(id,confirmation)) != 0){
            return "redirect:/department_head/?error=code_"+err;
        }
        return "redirect:/department_head/?prolongation_application_approved="+confirmation;
    }

    /*
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
