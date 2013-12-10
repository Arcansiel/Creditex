package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.OperatorService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

    private Integer getCredit(HttpSession session){
        return (Integer)session.getAttribute("operation_manager_credit");
    }
    private void setCredit(HttpSession session, Integer credit){
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
            ,@RequestParam("first")String first
            ,@RequestParam("last")String last
            ,@RequestParam("patronymic")String patronymic
            ,@RequestParam("series")String series
            ,@RequestParam("number")int number
    ){
        setCredit(session,null);
        User client = userService.GetUserByUserDataValues(first,last,patronymic,series,number);
        Credit credit;
        if(client == null){
            return "redirect:/operation_manager/?error=no client found";
        }else{
            credit = operatorService.CurrentCredit(client.getId());
            if(credit == null){
                //no current credit
                return "redirect:/operation_manager/?error=no current credit found";
            }else{
                Integer credit_id = credit.getId();
                setCredit(session,credit_id);
                return "redirect:/operation_manager/operation/list/";
            }
        }
    }

    @RequestMapping(value = {"/operation_manager/operation/list/"}, method = RequestMethod.GET)
    public String OperationManagerOperationList(HttpSession session, Model model){
        //get credit from session
        Integer credit_id;
        if((credit_id = getCredit(session)) != null){
            List<Operation> operations = operatorService.CreditOperations(credit_id);
            List<Payment> payments = operatorService.NearestPayments(credit_id);
            //push to model
            model.addAttribute("operations",operations);
            model.addAttribute("payments",payments);
            return "operation_manager_operation_list";
        }else{
            //push error : credit not selected
            return "redirect:/operation_manager/?error=credit not selected";
        }

    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.GET)
    public String OperationManagerOperation(HttpSession session, Model model){
        //get credit from session
        Integer credit_id;
        if((credit_id = getCredit(session)) != null){
            Credit credit = operatorService.getCredit(credit_id);
            Payment payment = operatorService.CurrentPayment(credit_id);
            if(payment != null){
                model.addAttribute("payment",payment);
            }
            model.addAttribute("credit",credit);
            model.addAttribute("expired",credit.getMainFine() > 0);
            return "operation_manager_operation";
        }else{
            //push error : credit not selected
            return "redirect:/operation_manager/?error=credit not selected";
        }

    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.POST)
    public String OperationManagerOperation(HttpSession session, Principal principal
            ,@RequestParam("type")OperationType type
            ,@RequestParam("amount")int amount
    ){
        //get credit from session
        Integer credit_id;
        if((credit_id = getCredit(session)) != null){
            int err;
            if((err=operatorService.ExecuteOperation(principal.getName(),credit_id,type,amount)) != 0){
                return "redirect:/operation_manager/operation/?error=operation not executed "+err;
            }
        }else{
            return "redirect:/operation_manager/?error=credit not selected";
        }
        return "redirect:/operation_manager/operation/?operation_executed=true";
    }

}
