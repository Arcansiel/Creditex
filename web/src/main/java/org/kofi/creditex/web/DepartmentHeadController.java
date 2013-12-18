package org.kofi.creditex.web;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.*;
import org.kofi.creditex.web.model.ConfirmationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_DEPARTMENT_HEAD")
public class DepartmentHeadController {

    @Autowired
    ProductService productService;

    @Autowired
    DepartmentHeadService departmentHeadService;

    @Autowired
    SecurityService securityService;

    @Autowired
    CreditService creditService;

    @Autowired
    UserService userService;

    @RequestMapping("/department_head/")
    public String MainDepartmentHead(){
        return "department_head";
    }

    @RequestMapping(value = "/department_head/appliances/committee_approved/", method = RequestMethod.GET)
    public String DepartmentHead1(Model model){
        model.addAttribute("applications",departmentHeadService.GetCommitteeApprovedUncheckedApplications());
        return "department_head_committee_approved";
    }

    @RequestMapping(value = "/department_head/appliances/committee_rejected/", method = RequestMethod.GET)
    public String DepartmentHead2(Model model){
        model.addAttribute("applications",departmentHeadService.GetCommitteeRejectedApplications());
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
            return "redirect:/department_head/?error=no_application&info="+id;
        }

    }

    @RequestMapping(value = "/department_head/appliance/{id}/set_head_approved/", method = RequestMethod.POST)
    public String DepartmentHeadA(Model model, Principal principal,
                                  @PathVariable("id")long id,
                                  @Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data";
        }
        int err;
        if((err = departmentHeadService.SetApplicationHeadApproved(id,principal.getName(),form.isAcceptance(),form.getComment())) != 0){
            return "redirect:/department_head/?error=head_acceptance_failed&info="+err;
        }
        return "redirect:/department_head/?application_approved="+form.isAcceptance();
    }

    @RequestMapping(value = {"/department_head/product/list/","/department_head/product/list/activated/"}, method = RequestMethod.GET)
    public String DepartmentHead31(Model model){
        model.addAttribute("products",productService.GetProductsByActive(true));
        return "department_head_product_list";
    }

    @RequestMapping(value = "/department_head/product/list/deactivated/", method = RequestMethod.GET)
    public String DepartmentHead32(Model model){
        model.addAttribute("products",productService.GetProductsByActive(false));
        return "department_head_product_list_deactivated";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.GET)
    public String DepartmentHead4(){
        return "department_head_product_creation";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.POST)
    public String DepartmentHead41(
                                  @Valid @ModelAttribute Product productForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data";
        }
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
            return "redirect:/department_head/?error=no_prolongation_application&info="+id;
        }
    }

    @RequestMapping(value = "/department_head/prolongation/{id}/set_head_approved/", method = RequestMethod.POST)
    public String DepartmentHeadProlongationApprove(Model model,
                                  @PathVariable("id")long id
                                  ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data";
        }
        int err;
        if((err = departmentHeadService.SetProlongationApproved(id,form.isAcceptance())) != 0){
            return "redirect:/department_head/?error=prolongation_acceptance_failed&info="+err;
        }
        return "redirect:/department_head/?prolongation_application_approved="+form.isAcceptance();
    }


    @RequestMapping(value = "/department_head/report/", method = RequestMethod.GET)
    public String DepartmentHead11(Model model){
        return "department_head_report";
    }

    @RequestMapping(value = "/department_head/credits/active/list/", method = RequestMethod.GET)
    public String DepartmentHead12(Model model){
        model.addAttribute("credits",creditService.GetCreditsByActive(true));
        return "department_head_credits_active_list";
    }

    @RequestMapping(value = "/department_head/credits/returned/list/", method = RequestMethod.GET)
    public String DepartmentHead13(Model model){
        model.addAttribute("credits",creditService.GetCreditsByActive(false));
        return "department_head_credits_returned_list";
    }

    @RequestMapping(value = "/department_head/clients/list/", method = RequestMethod.GET)
    public String DepartmentHead14(Model model){
        model.addAttribute("clients",userService.GetAllUsersByAuthority("ROLE_CLIENT"));
        return "department_head_clients_list";
    }

    @RequestMapping(value = "/department_head/client/{id}", method = RequestMethod.GET)
    public String DepartmentHead15(Model model
                    ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        long client_id = client.getId();
        long payments_count = securityService.GetClientPaymentsCount(client_id);
        long expired_payments_count = securityService.GetClientExpiredPaymentsCount(client_id);
        model.addAttribute("payments_count", payments_count);
        model.addAttribute("expired_payments_count", expired_payments_count);

        return "department_head_client_view";
    }


    @RequestMapping(value = "/department_head/client/{id}/credits/all/", method = RequestMethod.GET)
    public String DepartmentHead_list1(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",creditService.GetCreditsByUserId(client.getId()));

        return "department_head_client_credit_list";
    }

    @RequestMapping(value = "/department_head/client/{id}/credits/expired/", method = RequestMethod.GET)
    public String DepartmentHead_list2(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",securityService.GetClientExpiredCredits(client.getId()));

        return "department_head_client_credit_list";
    }

    @RequestMapping(value = "/department_head/client/{id}/prolongations/", method = RequestMethod.GET)
    public String DepartmentHead_list3(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("prolongations",securityService.GetClientProlongationApplications(client.getId()));

        return "department_head_client_prolongations_list";
    }

    @RequestMapping(value = "/department_head/client/{id}/priors/", method = RequestMethod.GET)
    public String DepartmentHead_list4(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("priors",securityService.GetClientPriorRepaymentApplications(client.getId()));

        return "department_head_client_priors_list";
    }


    @RequestMapping(value = {"/department_head/client/search/"}, method = RequestMethod.GET)
    public String Security81(){
        return "department_head_client_search";
    }

    @RequestMapping(value = {"/department_head/client/search/"}, method = RequestMethod.POST)
    public String Security82(@Valid @ModelAttribute UserData form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/client/search/?error=invalid_input_data";
        }
        User client = userService.GetUserByUserDataValues(form);
        if(client != null){
            return "redirect:/department_head/client/"+client.getId();
        }else{
            return "redirect:/department_head/client/search/?info=no_client";
        }
    }

    @RequestMapping(value = "/department_head/prior/list/", method = RequestMethod.GET)
    public String DepartmentHeadPList(Model model){
        model.addAttribute("priors",departmentHeadService.GetUncheckedPriors());
        return "department_head_prior_list_view";
    }

    @RequestMapping(value = "/department_head/prior/{id}", method = RequestMethod.GET)
    public String DepartmentHeadPrior(Model model,
                                  @PathVariable("id")long id){
        PriorRepaymentApplication prior = departmentHeadService.GetPrior(id);
        if(prior != null){
            model.addAttribute("prior",prior);
            return "department_head_prior_view";
        }else{
            return "redirect:/department_head/?error=no_prior_repayment_application&info="+id;
        }
    }

    @RequestMapping(value = "/department_head/prior/{id}/set_head_approved/", method = RequestMethod.POST)
    public String DepartmentHeadPriorApprove(Model model,
                                                    @PathVariable("id")long id
            ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data";
        }
        int err;
        if((err = departmentHeadService.SetPriorApproved(id,form.isAcceptance())) != 0){
            return "redirect:/department_head/?error=prior_acceptance_failed&info="+err;
        }
        return "redirect:/department_head/?prior_repayment_application_approved="+form.isAcceptance();
    }
}
