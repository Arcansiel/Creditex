package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.Dates;
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

    private Integer getClient(HttpSession session){
        return (Integer)session.getAttribute("operation_manager_client");
    }
    private void setClient(HttpSession session, Integer client){
        if(client == null){
            session.removeAttribute("operation_manager_client");
        }else{
            session.setAttribute("operation_manager_client",client);
        }
    }

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
    public String MainOperationManager(HttpSession session, Model model){
        return "operation_manager";
    }

    @RequestMapping(value = {"/operation_manager/"}, method = RequestMethod.POST)
    public String MainOperationManager(HttpSession session, Model model
            ,@RequestParam("first")String first
            ,@RequestParam("last")String last
            ,@RequestParam("patronymic")String patronymic
            ,@RequestParam("series")String series
            ,@RequestParam("number")int number
    ){
        String error = null;
        setClient(session,null);
        setCredit(session,null);

        Integer client_id = null, credit_id = null;
        User client = userService.GetUserByUserDataValues(first,last,patronymic,series,number);
        Credit credit = null;
        if(client == null){
            error = String.format("NO CLIENT FOUND: %s %s %s %s%d",first,last,patronymic,series,number);
        }else{
            client_id = client.getId();
            credit = operatorService.CurrentCredit(client_id);
            if(credit == null){
                //no current credit
                return "redirect:/operation_manager/?has_current_credit=false";
            }else{
                credit_id = credit.getId();
            }
        }
        if(error == null){
            //push data to session
            setClient(session,client_id);
            setCredit(session,credit_id);
            //redirect to list
            return "redirect:/operation_manager/operation/list/";
        }else{
            //push error to model
            model.addAttribute("error",error);
            return "operation_manager";
        }
    }

    @RequestMapping(value = {"/operation_manager/operation/list/"}, method = RequestMethod.GET)
    public String OperationManagerOperationList(HttpSession session, Model model){
        //get client from session
        Integer client = null;
        if((client = getClient(session)) != null){
            Integer credit = getCredit(session);
            List<Operation> operations = operatorService.CreditOperations(credit);
            //push operations to model
            model.addAttribute("operations",operations);
        }else{
            //push error : client not selected
            model.addAttribute("error","ERROR MESSAGE: client not selected");
        }
        return "operation_manager_operation_list";
    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.GET)
    public String OperationManagerOperation(HttpSession session, Model model){
        //get client from session
        Integer client = null;
        if((client = getClient(session)) != null){
            Integer credit = getCredit(session);
            int[] r = operatorService.CurrentPayment(credit, Dates.now());
            int payment = r[0];
            int creditpayment = r[0] - r[1] - r[2];
            int percentspayment = r[2];
            int finepayment = r[1];
            //push info to model
            model.addAttribute("credit",operatorService.getCredit(credit));
            model.addAttribute("payment",payment);
            model.addAttribute("creditpayment",creditpayment);
            model.addAttribute("percentspayment",percentspayment);
            model.addAttribute("finepayment",finepayment);
        }else{
            //push error : client not selected
            model.addAttribute("error","ERROR MESSAGE: client not selected");
        }
        return "operation_manager_operation";
    }

    @RequestMapping(value = {"/operation_manager/operation/"}, method = RequestMethod.POST)
    public String OperationManagerOperation(HttpSession session, Model model, Principal principal
            ,@RequestParam("type")OperationType type
            ,@RequestParam("amount")int amount
    ){
        String error = null;
        //get client from session
        Integer client = null;
        if((client = getClient(session)) != null){
            Integer credit = getCredit(session);
            OperationManagerController.log.info("Execute operation");
            if(operatorService.ExecuteOperation(principal.getName(),credit,Dates.now(),type,amount) != 0){
                error = "ERROR MESSAGE: operation not executed";
            }
        }else{
            error = "ERROR MESSAGE: client not selected";
        }
        if(error != null){
            //push error
            model.addAttribute("error",error);
            return "operation_manager_operation";
        }else{
            return "redirect:/operation_manager/operation/list/?operation_executed=true";
        }
    }

}
