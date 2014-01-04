package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.OperatorService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_OPERATION_MANAGER")
@Slf4j
public class OperationManagerController {

    @Autowired
    UserService userService;

    @Autowired
    OperatorService operatorService;

    private Long getCredit(HttpSession session){
        return (Long)session.getAttribute("operation_manager_credit");
    }
    private void setCredit(HttpSession session, Long credit){
        if(credit == null){
            session.removeAttribute("operation_manager_credit");
        }else{
            session.setAttribute("operation_manager_credit",credit);
        }
    }

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            switch (error) {
                case "invalid_input_data":
                    model.addAttribute("error", "Введены некорректные данные");
                    if (info != null) {
                        model.addAttribute("info", "Введены некорректные данные в поле " + info);
                    }
                    break;
                case "no_client":
                    model.addAttribute("error", "Клиент не найден в системе");
                    break;
                case "credit_not_selected":
                    model.addAttribute("error", "Кредит не выбран");
                    break;
                case "operation_not_executed":
                    model.addAttribute("error", "Операция не выполнена");
                    if (info != null) {
                        switch (info) {
                            case "-1":
                                model.addAttribute("info", "Значение суммы меньше либо равно нулю");
                                break;
                            case "-2":
                                model.addAttribute("info", "Операциониста нет в системе");
                                break;
                            case "-3":
                                model.addAttribute("info", "Кредит не существует");
                                break;
                            case "-4":
                                model.addAttribute("info", "Кредит закрыт, операции не разрешены");
                                break;
                            case "-5":
                            case "-6":
                            case "-7":
                                model.addAttribute("info", "Введена неверная сумма платежа");
                                break;
                            case "-8":
                                model.addAttribute("info", "На счёте недостаточно денег");
                                break;
                        }
                    }
                    break;
            }
        }else if(info != null){
            switch (info) {
                case "no_current_credit":
                    model.addAttribute("info", "У клиента нет текущего кредита");
                    break;
                case "no_payments_available":
                    model.addAttribute("info", "В данный момент нет доступных платежей");
                    break;
                case "operation_executed":
                    model.addAttribute("info", "Операция выполнена успешно");
                    break;
                case "credit_closed":
                    model.addAttribute("info", "Операция выполнена, кредит закрыт");
                    break;
            }
        }
    }

    @RequestMapping(value = {"/operation_manager/"}, method = RequestMethod.GET)
    public String MainOperationManager(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model, error, info);
        return "operation_manager";
    }

    @RequestMapping(value = {"/operation_manager/"}, method = RequestMethod.POST)
    public String MainOperationManager(HttpSession session
            ,@Valid @ModelAttribute UserData form, BindingResult bindingResult
            ){
        setCredit(session,null);
        if(bindingResult.hasErrors()){
            return "redirect:/operation_manager/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        User client = userService.GetClientByUserDataValues(form);
        if(client == null){
            return "redirect:/operation_manager/?error=no_client";
        }else{
            Credit credit = operatorService.CurrentCredit(client.getId());
            if(credit == null){
                //no current credit
                return "redirect:/operation_manager/?info=no_current_credit";
            }else{
                Long credit_id = credit.getId();
                setCredit(session,credit_id);
                return "redirect:/operation_manager/operation/";
            }
        }
    }

    @RequestMapping(value = {"/operation_manager/payments/"}, method = RequestMethod.GET)
    public String OperationManagerNearestPayments(HttpSession session, Model model){
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            List<Payment> payments = operatorService.NearestPayments(credit_id);
            //push to model
            model.addAttribute("payments",payments);
            return "operation_manager_payment_list";
        }else{
            //push error : credit not selected
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

    @RequestMapping(value = {"/operation_manager/operation/list/"}, method = RequestMethod.GET)
    public String OperationManagerOperationList(HttpSession session, Model model){
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            List<Operation> operations = operatorService.CreditOperations(credit_id);
            //push to model
            model.addAttribute("operations",operations);
            return "operation_manager_operation_list";
        }else{
            //push error : credit not selected
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.GET)
    public String OperationManagerOperation(HttpSession session, Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            Credit credit = operatorService.getCredit(credit_id);
            model.addAttribute("credit",credit);
            Payment payment = operatorService.CurrentPayment(credit_id);
            if(payment != null){
                model.addAttribute("payment",payment);
            }
            return "operation_manager_operation";
        }else{
            //push error : credit not selected
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.POST)
    public String OperationManagerOperation(HttpSession session, Principal principal
            ,@Valid @ModelAttribute Operation form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/operation_manager/operation/?error=invalid_input_data&info="+bindingResult.getFieldError().getField()+"#error_info";
        }
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            int code;
            if((code=operatorService.ExecuteOperation(principal.getName(),credit_id,form.getType(),form.getAmount())) == 0){
                return "redirect:/operation_manager/operation/?info=operation_executed#error_info";
            }else{
                return "redirect:/operation_manager/operation/?error=operation_not_executed&info="+code+"#error_info";
            }
        }else{
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

}
