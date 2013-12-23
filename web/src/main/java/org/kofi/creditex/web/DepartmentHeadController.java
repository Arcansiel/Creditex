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

    @Autowired
    StatisticsService statisticsService;

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            if(error.equals("no_application")){
                model.addAttribute("error","Заявка на кредит не найдена");
                if(info != null){
                    model.addAttribute("info","ID заявки: "+info);
                }
            }else if(error.equals("no_prior_repayment_application")){
                model.addAttribute("error","Заявка на досрочное погашение не найдена");
                if(info != null){
                    model.addAttribute("info","ID заявки: "+info);
                }
            }else if(error.equals("no_prolongation_application")){
                model.addAttribute("error","Заявка на пролонгацию не найдена");
                if(info != null){
                    model.addAttribute("info","ID заявки: "+info);
                }
            }else if(error.equals("no_client")){
                model.addAttribute("error","Клиент не найден в системе");
                if(info != null){
                    model.addAttribute("info","ID клиента: "+info);
                }
            }else if(error.equals("invalid_input_data")){
                model.addAttribute("error","Введены некорректные данные");
                if(info != null){
                    model.addAttribute("info","Введены некорректные данные в поле "+info);
                }
            }else if(error.equals("head_acceptance_failed")){
                model.addAttribute("error","Ошибка принятия решения по заявке на кредит");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","ГКО отсутствует в системе");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Заявка не найдена");
                    }else if(info.equals("-3")){
                        model.addAttribute("info","Заявка не находится на стадии рассмотрения ГКО");
                    }else if(info.equals("-4")){
                        model.addAttribute("info","Голосование по заявке ещё не завершено");
                    }
                }
            }else if(error.equals("prior_acceptance_failed")){
                model.addAttribute("error","Ошибка принятия решения по заявке на досрочное погашение");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","Заявка на досрочное погашение не найдена");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Заявка на досрочное погашение не находится на стадии рассмотрения");
                    }
                }
            }else if(error.equals("prolongation_acceptance_failed")){
                model.addAttribute("error","Ошибка принятия решения по заявке на пролонгацию");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","Заявка на пролонгацию не найдена");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Заявка на пролонгацию не находится на стадии рассмотрения");
                    }
                }
            }else if(error.equals("product_creation_failed")){
                model.addAttribute("error","Ошибка создания кредитного продукта");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","Имя кредитного продкта не должно быть пустым");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Неверные значени для границ суммы кредита (или меньше нуля, или минимальное больше максимального)");
                    }else if(info.equals("-3")){
                        model.addAttribute("info","Неверные значени для границ срока кредитования (или меньше нуля, или минимальное больше максимального)");
                    }else if(info.equals("-4")){
                        model.addAttribute("info","Неверное значение процентных полей кредита (меньше нуля)");
                    }else if(info.equals("-5")){
                        model.addAttribute("info","Кредитный продукт с таким именем уже существует");
                    }
                }
            }else if(error.equals("product_state_changing_failed")){
                model.addAttribute("error","Ошибка изменения состояния кредитного продукта");
                if(info != null){
                    model.addAttribute("info","Кредитный продукт не существует, ID : "+info);
                }
            }
        }else if(info != null){
            if(info.equals("application_accepted")){
                model.addAttribute("info","Заявка на кредит принята");
            }else if(info.equals("application_rejected")){
                model.addAttribute("info","Заявка на кредит отклонена");
            }else if(info.equals("prior_repayment_application_accepted")){
                model.addAttribute("info","Заявка на досрочное погашение принята");
            }else if(info.equals("prior_repayment_application_rejected")){
                model.addAttribute("info","Заявка на досрочное погашение отклонена");
            }else if(info.equals("prolongation_application_accepted")){
                model.addAttribute("info","Заявка на пролонгацию принята");
            }else if(info.equals("prolongation_application_rejected")){
                model.addAttribute("info","Заявка на пролонгацию отклонена");
            }else if(info.equals("product_created")){
                model.addAttribute("info","Кредитный продукт создан");
            }else if(info.equals("product_state_active")){
                model.addAttribute("info","Состояние кредитного продукта установлено: Активный");
            }else if(info.equals("product_state_inactive")){
                model.addAttribute("info","Состояние кредитного продукта установлено: Неактивный");
            }
        }
    }

    @RequestMapping("/department_head/")
    public String MainDepartmentHead(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
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
    public String DepartmentHeadA(Principal principal,
                                  @PathVariable("id")long id,
                                  @Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = departmentHeadService.SetApplicationHeadApproved(id,principal.getName(),form.isAcceptance(),form.getComment())) != 0){
            return "redirect:/department_head/?error=head_acceptance_failed&info="+err;
        }
        if(form.isAcceptance()){
            return "redirect:/department_head/?info=application_accepted";
        }else{
            return "redirect:/department_head/?info=application_rejected";
        }
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


    @RequestMapping(value = "/department_head/statistics/products/", method = RequestMethod.GET)
    public String DepartmentHead33(Model model){
        long[] out = new long[3];
        model.addAttribute("statistics",statisticsService.GetAllProductsStatistics(out));
        model.addAttribute("countAll",out[0]);
        model.addAttribute("countActive",out[1]);
        model.addAttribute("countInActive",out[2]);
        return "department_head_statistics_products";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.GET)
    public String DepartmentHead4(){
        return "department_head_product_creation";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.POST)
    public String DepartmentHead41(
                                  @Valid @ModelAttribute Product productForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = productService.CreateProductByForm(productForm)) == 0){
            return "redirect:/department_head/?info=product_created";
        }else{
            return "redirect:/department_head/?error=product_creation_failed&info="+err;
        }

    }

    @RequestMapping(value = "/department_head/product/{id}/set_active/{active}", method = {RequestMethod.GET, RequestMethod.POST})
    public String DepartmentHead7(
            @PathVariable("id")long id,
            @PathVariable("active")boolean active){
        if(productService.SetProductIsActive(id, active)){
            if(active){
                return "redirect:/department_head/?info=product_state_active";
            }else{
                return "redirect:/department_head/?info=product_state_inactive";
            }
        }else{
            return "redirect:/department_head/?error=product_state_changing_failed&info="+id;
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
    public String DepartmentHeadProlongationApprove(
                                  @PathVariable("id")long id
                                  ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = departmentHeadService.SetProlongationApproved(id,form.isAcceptance())) != 0){
            return "redirect:/department_head/?error=prolongation_acceptance_failed&info="+err;
        }
        if(form.isAcceptance()){
            return "redirect:/department_head/?info=prolongation_application_accepted";
        }else{
            return "redirect:/department_head/?info=prolongation_application_rejected";
        }
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

    @RequestMapping(value = "/department_head/client/{id}/statistics/", method = RequestMethod.GET)
    public String ClientStatistics(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/department_head/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",statisticsService.GetClientCreditsStatistics(id));
        model.addAttribute("applications",statisticsService.GetClientApplicationsStatistics(id));
        model.addAttribute("priors",statisticsService.GetClientPriorsStatistics(id));
        model.addAttribute("prolongations",statisticsService.GetClientProlongationsStatistics(id));
        model.addAttribute("payments",statisticsService.GetClientPaymentsStatistics(id));
        return "department_head_statistics_client";
    }

    @RequestMapping(value = {"/department_head/client/search/"}, method = RequestMethod.GET)
    public String Security81(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        return "department_head_client_search";
    }

    @RequestMapping(value = {"/department_head/client/search/"}, method = RequestMethod.POST)
    public String Security82(@Valid @ModelAttribute UserData form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/client/search/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        User client = userService.GetUserByUserDataValues(form);
        if(client != null){
            return "redirect:/department_head/client/"+client.getId();
        }else{
            return "redirect:/department_head/client/search/?error=no_client";
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
    public String DepartmentHeadPriorApprove(
            @PathVariable("id")long id
            ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = departmentHeadService.SetPriorApproved(id,form.isAcceptance())) != 0){
            return "redirect:/department_head/?error=prior_acceptance_failed&info="+err;
        }
        if(form.isAcceptance()){
            return "redirect:/department_head/?info=prior_repayment_application_accepted";
        }else{
            return "redirect:/department_head/?info=prior_repayment_application_rejected";
        }
    }

    @RequestMapping(value = "/department_head/statistics/", method = RequestMethod.GET)
    public String DepartmentHeadStatistics(Model model){
        model.addAttribute("credits",statisticsService.GetCreditsStatistics());
        model.addAttribute("clients",statisticsService.GetClientsStatistics());
        model.addAttribute("applications",statisticsService.GetApplicationsStatistics());
        model.addAttribute("priors",statisticsService.GetPriorsStatistics());
        model.addAttribute("prolongations",statisticsService.GetProlongationsStatistics());
        model.addAttribute("payments",statisticsService.GetPaymentsStatistics());
        return "department_head_statistics";
    }


    @RequestMapping(value = "/department_head/report/", method = RequestMethod.GET)
    public String DepartmentHeadReport(Model model
            ,@RequestParam(value = "period", required = false)Long period
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        //TODO report controller implementation
        if(error == null && period != null){
            if(period < 1){
                return "redirect:/department_head/report/?error=invalid_input_data&info=period";
            }
            //TODO ...
        }//else return empty form
        return "department_head_report";
    }
}
