package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.Dates;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_OPERATION_MANAGER")
@Slf4j
public class OperationManagerController {

    @Autowired
    UserService userService;

    private User getClient(HttpSession session){
        return (User)session.getAttribute("operation_manager_client");
    }

    private void setClient(HttpSession session, User client){
        if(client == null){
            session.removeAttribute("operation_manager_client");
        }else{
            session.setAttribute("operation_manager_client",client);
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
                                       ,@RequestParam("number")Integer number
    ){
        String error = null;
        setClient(session,null);

        User client = userService.GetUserByUserDataValues(first,last,patronymic,series,number);
        if(client == null){
            error = String.format("NO CLIENT FOUND: %s %s %s %s%d",first,last,patronymic,series,number);
        }
        if(error == null){
            //push client to session
            setClient(session,client);
            //redirect to list
            return "redirect:/operation_manager_operation_list/";
        }else{
            //push error to model
            model.addAttribute("error",error);
            return "operation_manager";
        }
    }

    @RequestMapping(value = {"/operation_manager_operation_list/"}, method = RequestMethod.GET)
    public String OperationManagerOperationList(HttpSession session, Model model){
        //get client from session
        User client = null;
        if((client = getClient(session)) != null){
            //TODO service: find client operations
            List<Operation> operations = new ArrayList<Operation>();
            operations.add(Operation.builder().amount(1000000).operationDate(Dates.now(-60)).type(OperationType.Withdrawal).build());
            operations.add(Operation.builder().amount(10000).operationDate(Dates.now(-30)).type(OperationType.Deposit).build());
            //push operations to model
            model.addAttribute("operations",operations);
        }else{
            //push error : client not selected
            model.addAttribute("error","ERROR MESSAGE: client not selected");
        }
        return "operation_manager_operation_list";
    }

    @RequestMapping(value = {"/operation_manager_operation/","/operation_manager_operation"}, method = RequestMethod.GET)
    public String OperationManagerOperation(HttpSession session, Model model){
        //get client from session
        User client = null;
        if((client = getClient(session)) != null){
            //? push operation types to model
            //push client info to model
            model.addAttribute("client",client);
        }else{
            //push error : client not selected
            model.addAttribute("error","ERROR MESSAGE: client not selected");
        }
        return "operation_manager_operation";
    }

    @RequestMapping(value = {"/operation_manager_operation/"}, method = RequestMethod.POST)
    public String OperationManagerOperation(HttpSession session, Model model
            ,@RequestParam("type")OperationType type
            ,@RequestParam("amount")Integer amount
    ){
        String error = null;
        //get client from session
        User client = null;
        if((client = getClient(session)) != null){
            OperationManagerController.log.info("Execute operation");
            //TODO service: execute operation
            if(type == OperationType.Withdrawal){
                error = "ERROR MESSAGE: operation error (Withdrawal)";
            }
        }else{
            error = "ERROR MESSAGE: client not selected";
        }
        if(error != null){
            //push error
            model.addAttribute("error",error);
            return "operation_manager_operation";
        }else{
            return "redirect:/operation_manager_operation_list/";
        }
    }

}
