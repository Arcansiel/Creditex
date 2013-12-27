package org.kofi.creditex.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.*;
import org.kofi.creditex.web.model.ConfirmationForm;
import org.kofi.creditex.web.model.ReportRequestForm;
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

    @Autowired
    DayReportService dayReportService;

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            switch (error) {
                case "no_application":
                    model.addAttribute("error", "Заявка на кредит не найдена");
                    if (info != null) {
                        model.addAttribute("info", "ID заявки: " + info);
                    }
                    break;
                case "no_prior_repayment_application":
                    model.addAttribute("error", "Заявка на досрочное погашение не найдена");
                    if (info != null) {
                        model.addAttribute("info", "ID заявки: " + info);
                    }
                    break;
                case "no_prolongation_application":
                    model.addAttribute("error", "Заявка на пролонгацию не найдена");
                    if (info != null) {
                        model.addAttribute("info", "ID заявки: " + info);
                    }
                    break;
                case "no_client":
                    model.addAttribute("error", "Клиент не найден в системе");
                    if (info != null) {
                        model.addAttribute("info", "ID клиента: " + info);
                    }
                    break;
                case "invalid_input_data":
                    model.addAttribute("error", "Введены некорректные данные");
                    if (info != null) {
                        model.addAttribute("info", "Введены некорректные данные в поле " + info);
                    }
                    break;
                case "head_acceptance_failed":
                    model.addAttribute("error", "Ошибка принятия решения по заявке на кредит");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "ГКО отсутствует в системе");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка не найдена");
                                break;
                            case "-3":
                                model.addAttribute("info", "Заявка не находится на стадии рассмотрения ГКО");
                                break;
                            case "-4":
                                model.addAttribute("info", "Голосование по заявке ещё не завершено");
                                break;
                        }
                    }
                    break;
                case "prior_acceptance_failed":
                    model.addAttribute("error", "Ошибка принятия решения по заявке на досрочное погашение");
                    if (info != null) {
                        switch (info){
                            case "-1":
                                model.addAttribute("info", "Заявка на досрочное погашение не найдена");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка на досрочное погашение не находится на стадии рассмотрения");
                                break;
                        }
                    }
                    break;
                case "prolongation_acceptance_failed":
                    model.addAttribute("error", "Ошибка принятия решения по заявке на пролонгацию");
                    if (info != null) {
                        switch (info){
                            case "-1":
                                model.addAttribute("info", "Заявка на пролонгацию не найдена");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка на пролонгацию не находится на стадии рассмотрения");
                                break;
                        }
                    }
                    break;
                case "product_creation_failed":
                    model.addAttribute("error", "Ошибка создания кредитного продукта");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Имя кредитного продкта не должно быть пустым");
                                break;
                            case "-2":
                                model.addAttribute("info", "Неверные значени для границ суммы кредита (или меньше нуля, или минимальное больше максимального)");
                                break;
                            case "-3":
                                model.addAttribute("info", "Неверные значени для границ срока кредитования (или меньше нуля, или минимальное больше максимального)");
                                break;
                            case "-4":
                                model.addAttribute("info", "Неверное значение процентных полей кредита (меньше нуля)");
                                break;
                            case "-5":
                                model.addAttribute("info", "Кредитный продукт с таким именем уже существует");
                                break;
                        }
                    }
                    break;
                case "product_state_changing_failed":
                    model.addAttribute("error", "Ошибка изменения состояния кредитного продукта");
                    if (info != null) {
                        model.addAttribute("info", "Кредитный продукт не существует, ID : " + info);
                    }
                    break;
            }
        }else if(info != null){
            switch (info) {
                case "application_accepted":
                    model.addAttribute("info", "Заявка на кредит принята");
                    break;
                case "application_rejected":
                    model.addAttribute("info", "Заявка на кредит отклонена");
                    break;
                case "prior_repayment_application_accepted":
                    model.addAttribute("info", "Заявка на досрочное погашение принята");
                    break;
                case "prior_repayment_application_rejected":
                    model.addAttribute("info", "Заявка на досрочное погашение отклонена");
                    break;
                case "prolongation_application_accepted":
                    model.addAttribute("info", "Заявка на пролонгацию принята");
                    break;
                case "prolongation_application_rejected":
                    model.addAttribute("info", "Заявка на пролонгацию отклонена");
                    break;
                case "product_created":
                    model.addAttribute("info", "Кредитный продукт создан");
                    break;
                case "product_state_active":
                    model.addAttribute("info", "Состояние кредитного продукта установлено: Активный");
                    break;
                case "product_state_inactive":
                    model.addAttribute("info", "Состояние кредитного продукта установлено: Неактивный");
                    break;
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
    public String DepartmentHead4(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        return "department_head_product_creation";
    }

    @RequestMapping(value = "/department_head/product/create/", method = RequestMethod.POST)
    public String DepartmentHead41(
                                  @Valid @ModelAttribute Product productForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/product/create/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = productService.CreateProductByForm(productForm)) == 0){
            return "redirect:/department_head/?info=product_created";
        }else{
            return "redirect:/department_head/product/create/?error=product_creation_failed&info="+err;
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


    @RequestMapping(value = {"/department_head/report/","/department_head/report/form/"}, method = RequestMethod.GET)
    public String DepartmentHeadReportForm(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model, error, info);
        //return empty form
        return "department_head_report";
    }

    @RequestMapping(value = "/department_head/report/list/", method = RequestMethod.GET)
    public String DepartmentHeadReportGet(Model model
            ,@Valid @ModelAttribute ReportRequestForm reportRequest, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/department_head/report/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        //show last day reports
        model.addAttribute("period",reportRequest.getPeriod());
        List<DayReport> reports = dayReportService.GetLatestReportList(reportRequest.getPeriod());
        model.addAttribute("reports",reports);
        model.addAttribute("overall",true);

        try {
            model.addAttribute("report", dayReportService.ConvertReportListToJson(reports));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "department_head_report";
    }

}
