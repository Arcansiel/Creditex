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

    @RequestMapping(value = {"/operation_manager/"}, method = RequestMethod.GET)
    public String MainOperationManager(){
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
                return "redirect:/operation_manager/?error=no_current_credit";
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
    public String OperationManagerOperation(HttpSession session, Model model){
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
            if(payment != null){
                amount = payment.getRequiredPayment();
            }else if(expired){
                amount = credit.getMainFine() + credit.getPercentFine();
            }
            model.addAttribute("amount",amount);
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
            return "redirect:/operation_manager/operation/?error=invalid_input_data";
        }
        //get credit from session
        Long credit_id;
        if((credit_id = getCredit(session)) != null){
            int code;
            if((code=operatorService.ExecuteOperation(principal.getName(),credit_id,form.getType(),form.getAmount())) < 0){
                return "redirect:/operation_manager/operation/?error=operation_not_executed&info="+code;
            }else{
                if(code == 0){
                    return "redirect:/operation_manager/operation/?info=no_operations_available";
                }else if(code == 1){
                    return "redirect:/operation_manager/operation/?info=operation_executed";
                }else{
                    return "redirect:/operation_manager/?info=credit_closed";
                }
            }
        }else{
            return "redirect:/operation_manager/?error=credit_not_selected";
        }
    }

}
