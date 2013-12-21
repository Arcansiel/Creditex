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
            if(error.equals("invalid_input_data")){
                model.addAttribute("error","Введены некорректные данные");
            }else if(error.equals("no_client")){
                model.addAttribute("error","Клиент не найден в системе");
            }else if(error.equals("credit_not_selected")){
                model.addAttribute("error","Кредит не выбран");
            }else if(error.equals("operation_not_executed")){
                model.addAttribute("error","Операция не выполнена");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","Значение суммы меньше либо равно нулю");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Операциониста нет в системе");
                    }else if(info.equals("-3")){
                        model.addAttribute("info","Кредит не существует");
                    }else if(info.equals("-4")){
                        model.addAttribute("info","Кредит закрыт, операции не разрешены");
                    }else if(info.equals("-5") || info.equals("-6") || info.equals("-7")){
                        model.addAttribute("info","Введена неверная сумма платежа");
                    }else if(info.equals("-8")){
                        model.addAttribute("info","На счёте недостаточно денег");
                    }
                }
            }
        }else if(info != null){
            if(info.equals("no_current_credit")){
                model.addAttribute("info","У клиента нет текущего кредита");
            }if(info.equals("no_payments_available")){
                model.addAttribute("info","В данный момент нет доступных платежей");
            }else if(info.equals("operation_executed")){
                model.addAttribute("info","Операция выполнена успешно");
            }else if(info.equals("credit_closed")){
                model.addAttribute("info","Операция выполнена, кредит закрыт");
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
            return "redirect:/operation_manager/?error=invalid_input_data";
        }
        User client = userService.GetUserByUserDataValues(form);
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
                return "redirect:/operation_manager/payments/";
            }
        }
    }

    /*private void AddPriorRepaymentToModel(long credit_id, Model model){
        long[] prior_values = new long[2];
        PriorRepaymentApplication prior = operatorService.CurrentPriorRepayment(credit_id, prior_values);
        if(prior != null && prior_values[0] >= 0){
            model.addAttribute("prior",prior);
            model.addAttribute("priorAmount",prior_values[0]);
            model.addAttribute("priorFine",prior_values[1]);
        }
    }*/

    @RequestMapping(value = {"/operation_manager/payments/"}, method = RequestMethod.GET)
    public String OperationManagerNearestPayments(HttpSession session, Model model){
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            List<Payment> payments = operatorService.NearestPayments(credit_id);
            //push to model
            model.addAttribute("payments",payments);
            //AddPriorRepaymentToModel(credit_id, model);
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
            Payment payment = operatorService.CurrentPayment(credit_id);
            if(payment != null){
                model.addAttribute("payment",payment);
            }
            model.addAttribute("credit",credit);
            boolean expired = credit.getMainFine() > 0;
            model.addAttribute("expired", expired);
            long amount = 0;
            if(expired){
                amount = credit.getMainFine() + credit.getPercentFine();
            }else if(payment != null){
                amount = payment.getRequiredPayment();
            }
            if(amount > 0){
                model.addAttribute("amount",amount);
            }
            //AddPriorRepaymentToModel(credit_id, model);//and amount as prior value
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
            return "redirect:/operation_manager/operation/?error=invalid_input_data#error_info";
        }
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            int code;
            if((code=operatorService.ExecuteOperation(principal.getName(),credit_id,form.getType(),form.getAmount())) < 0){
                return "redirect:/operation_manager/operation/?error=operation_not_executed&info="+code+"#error_info";
            }else{
                if(code == 0){
                    return "redirect:/operation_manager/operation/?info=no_payments_available#error_info";
                }else if(code == 1){
                    return "redirect:/operation_manager/operation/?info=operation_executed#error_info";
                }else{
                    return "redirect:/operation_manager/?info=credit_closed";
                }
            }
        }else{
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

}
