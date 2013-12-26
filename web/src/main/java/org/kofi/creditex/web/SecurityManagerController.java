package org.kofi.creditex.web;



import org.kofi.creditex.model.*;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.SecurityService;
import org.kofi.creditex.service.StatisticsService;
import org.kofi.creditex.service.UserService;
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
@Secured("ROLE_SECURITY_MANAGER")
public class SecurityManagerController {

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    CreditService creditService;

    @Autowired
    StatisticsService statisticsService;

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            switch (error) {
                case "application_assignment_failed":
                    model.addAttribute("error", "Получение заявки не выполнено");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Специалист безопасности отсутствует в системе");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка не найдена");
                                break;
                            case "-3":
                                model.addAttribute("info", "Заявка не находится на стадии рассмотрения службой безопасности");
                                break;
                            case "-4":
                                model.addAttribute("info", "Заявка находится в рассмотрении у другого специалиста безопасности");
                                break;
                        }
                    }
                    break;
                case "no_application":
                    model.addAttribute("error", "Заявка не найдена");
                    if (info != null) {
                        model.addAttribute("info", "ID заявки: " + info);
                    }
                    break;
                case "application_assignment_cancel_failed":
                    model.addAttribute("error", "Ошибка при отказе от рассмотрения заявки");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Специалист безопасности отсутствует в системе");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка не найдена");
                                break;
                            case "-3":
                                model.addAttribute("info", "Заявка не находится на стадии рассмотрения службой безопасности");
                                break;
                            case "-4":
                                model.addAttribute("info", "Заявка находится в рассмотрении у другого специалиста безопасности");
                                break;
                        }
                    }
                    break;
                case "no_client":
                    model.addAttribute("error", "Клиент не найден в системе");
                    if (info != null) {
                        model.addAttribute("info", "ID клиента: " + info);
                    }
                    break;
                case "no_credit":
                    model.addAttribute("error", "Кредит не найден");
                    if (info != null) {
                        model.addAttribute("info", "ID кредита: " + info);
                    }
                    break;
                case "invalid_input_data":
                    model.addAttribute("error", "Введены некорректные данные");
                    if (info != null) {
                        model.addAttribute("info", "Введены некорректные данные в поле " + info);
                    }
                    break;
                case "confirmation_failed":
                    model.addAttribute("error", "Ошибка принятия решения по заявке");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Специалист безопасности отсутствует в системе");
                                break;
                            case "-2":
                                model.addAttribute("info", "Заявка не найдена");
                                break;
                            case "-3":
                                model.addAttribute("info", "Заявка не находится на стадии рассмотрения службой безопасности");
                                break;
                            case "-4":
                                model.addAttribute("info", "Заявка находится в рассмотрении у другого специалиста безопасности");
                                break;
                        }
                    }
                    break;
                case "notification_failed":
                    model.addAttribute("error", "Ошибка создания уведомления");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Специалист безопасности отсутствует в системе");
                                break;
                            case "-2":
                                model.addAttribute("info", "Кредит не найден");
                                break;
                            case "-3":
                                model.addAttribute("info", "Клиент не найден");
                                break;
                            case "-4":
                                model.addAttribute("info", "Нельзя отправлять уведомления по закрытым кредитам");
                                break;
                        }
                    }
                    break;
            }
        }else if(info != null){
            switch (info) {
                case "application_assignment_canceled":
                    model.addAttribute("info", "Отказ от рассмотрения заявки выполнен");
                    break;
                case "confirmation_completed":
                    model.addAttribute("info", "Решение по заявке принято");
                    break;
                case "no_assigned_application":
                    model.addAttribute("info", "Заявка для рассмотрения не выбрана");
                    break;
                case "notification_sent":
                    model.addAttribute("info", "Уведомление отправлено");
                    break;
            }
        }
    }

    @RequestMapping("/security_manager/")
    public String MainSecurityManager(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model, error, info);
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/appliance/current/", method = RequestMethod.GET)
    public String SecurityManagerCurrentApplication(Principal principal){
        Application application = securityService.GetSecurityAssignedApplication(principal.getName());
        if(application != null){
            return "redirect:/security_manager/appliance/check/"+application.getId();
        }else{
            return "redirect:/security_manager/?info=no_assigned_application";
        }
    }

    @RequestMapping(value = "/security_manager/appliances/", method = RequestMethod.GET)
    public String Security1(Model model, Principal principal){
        List<Application> applications = securityService.GetSecurityUncheckedApplications();
        model.addAttribute("applications",applications);
        Application current = securityService.GetSecurityAssignedApplication(principal.getName());
        if(current != null){
            model.addAttribute("current",current);
        }
        return "security_manager_appliances";
    }

    @RequestMapping(value = "/security_manager/credits/expired/", method = RequestMethod.GET)
    public String Security2(Model model){
        List<Credit> credits;
        credits = securityService.GetExpiredCredits();
        model.addAttribute("credits",credits);
        return "security_manger_credits_expired";
    }

    @RequestMapping(value = "/security_manager/credits/unreturned/", method = RequestMethod.GET)
    public String Security3(Model model){
        List<Credit> credits;
        credits = securityService.GetUnreturnedCredits();
        model.addAttribute("credits", credits);
        return "security_manager_credits_unreturned";
    }


    @RequestMapping(value = "/security_manager/appliance/check/{id}", method = RequestMethod.GET)
    public String Security4(Model model, Principal principal
                            ,@PathVariable("id")long id){
        int err = securityService.AssignApplicationToSecurity(id,principal.getName());
        if(err < 0){
            return "redirect:/security_manager/?error=application_assignment_failed&info="+err;
        }
        Application application = securityService.GetApplication(id);
        if(application == null){
            return "redirect:/security_manager/?error=no_application&info="+id;
        }
        model.addAttribute("application",application);
        return "security_manager_appliance_check";
    }

    @RequestMapping(value = "/security_manager/appliance/cancel_assignment/{id}", method = {RequestMethod.GET,RequestMethod.POST})
    public String Security41(Principal principal, @PathVariable("id")long id){
        int err = securityService.CancelApplicationAssignment(id,principal.getName());
        if(err < 0){
            return "redirect:/security_manager/?error=application_assignment_cancel_failed&info="+err;
        }else{
            return "redirect:/security_manager/?info=application_assignment_canceled";
        }
    }

    @RequestMapping(value = "/security_manager/client/check/{id}", method = RequestMethod.GET)
    public String Security51(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);

        long client_id = client.getId();
        Credit credit = securityService.GetCurrentClientCredit(client_id);
        if(credit != null){
            model.addAttribute("credit",credit);
        }
        List<Credit> unreturned = securityService.GetClientUnreturnedCredits(client_id);
        model.addAttribute("unreturned",unreturned);

        return "security_manager_client_check";
    }

    @RequestMapping(value = "/security_manager/client/check/outer/{id}", method = RequestMethod.GET)
    public String Security52(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("info",securityService.ClientOuterCheck(id));
        return "security_manager_client_check_outer";
    }

    @RequestMapping(value = "/security_manager/client/{id}/statistics/", method = RequestMethod.GET)
    public String ClientStatistics(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",statisticsService.GetClientCreditsStatistics(id));
        model.addAttribute("applications",statisticsService.GetClientApplicationsStatistics(id));
        model.addAttribute("priors",statisticsService.GetClientPriorsStatistics(id));
        model.addAttribute("prolongations",statisticsService.GetClientProlongationsStatistics(id));
        model.addAttribute("payments",statisticsService.GetClientPaymentsStatistics(id));
        return "security_manager_statistics_client";
    }

    @RequestMapping(value = "/security_manager/client/{id}/credits/all/", method = RequestMethod.GET)
    public String Security53(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",creditService.GetCreditsByUserId(client.getId()));

        return "security_manager_client_credit_list";
    }

    @RequestMapping(value = "/security_manager/client/{id}/credits/expired/", method = RequestMethod.GET)
    public String Security54(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",securityService.GetClientExpiredCredits(client.getId()));

        return "security_manager_client_credit_list";
    }

    @RequestMapping(value = "/security_manager/client/{id}/prolongations/", method = RequestMethod.GET)
    public String Security55(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("prolongations",securityService.GetClientProlongationApplications(client.getId()));

        return "security_manager_client_prolongations_list";
    }

    @RequestMapping(value = "/security_manager/client/{id}/priors/", method = RequestMethod.GET)
    public String Security56(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/security_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("priors",securityService.GetClientPriorRepaymentApplications(client.getId()));

        return "security_manager_client_priors_list";
    }

    @RequestMapping(value = "/security_manager/appliance/confirm/{id}", method = RequestMethod.POST)
    public String Security6(Principal principal
                            ,@PathVariable("id")long id
                            ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/security_manager/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = securityService.ConfirmApplication(principal.getName(),id,form.isAcceptance(),form.getComment())) == 0){
            return "redirect:/security_manager/?info=confirmation_completed";
        }else{
            return "redirect:/security_manager/?error=confirmation_failed&info="+err;
        }
    }

    @RequestMapping(value = "/security_manager/clients/list/", method = RequestMethod.GET)
    public String Security7(Model model){
        model.addAttribute("clients",userService.GetAllUsersByAuthority("ROLE_CLIENT"));
        return "security_manager_clients_list";
    }

    @RequestMapping(value = {"/security_manager/client/search/"}, method = RequestMethod.GET)
    public String Security81(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model, error, info);
        return "security_manager_client_search";
    }

    @RequestMapping(value = {"/security_manager/client/search/"}, method = RequestMethod.POST)
    public String Security82(@Valid @ModelAttribute UserData form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/security_manager/client/search/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        User client = userService.GetUserByUserDataValues(form);
        if(client != null){
            return "redirect:/security_manager/client/check/"+client.getId();
        }else{
            return "redirect:/security_manager/client/search/?error=no_client";
        }
    }

    @RequestMapping(value = "/security_manager/notification/credit/{id}", method = RequestMethod.GET)
    public String Security91(Model model
            ,@PathVariable("id")long id
            ,@RequestParam(value = "type", required = false)String type
    ){
        NotificationType notificationType = NotificationType.Expired;
        if(type != null){
            type = type.toLowerCase();
            if(type.equals("expired")){
                notificationType = NotificationType.Expired;
            }else if(type.equals("unreturned")){
                notificationType = NotificationType.Unreturned;
            }
        }
        Credit credit = creditService.GetCreditById(id);
        if(credit == null){
            return "redirect:/security_manager/?error=no_credit&info="+id;
        }
        model.addAttribute("type",notificationType);
        model.addAttribute("credit",credit);
        model.addAttribute("credit_notifications_count",securityService.GetCreditNotificationsCount(id));
        return "security_manager_notification";
    }

    @RequestMapping(value = "/security_manager/notification/credit/{id}", method = RequestMethod.POST)
    public String Security92(Principal principal
            ,@PathVariable("id")long id
            ,@Valid @ModelAttribute Notification notification, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/security_manager/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err = securityService.SendNotification(principal.getName(),id,
                notification.getType(),notification.getMessage());
        if(err < 0){
            return "redirect:/security_manager/?error=notification_failed&info="+err;
        }
        return "redirect:/security_manager/?info=notification_sent";
    }

}
