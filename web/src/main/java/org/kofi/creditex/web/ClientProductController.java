package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/client/product")
public class ClientProductController {
    @RequestMapping("/list")
    public String ListProducts(){
        //TODO
        return "";
    }

    @RequestMapping("/{id}/view")
    public String ShowProduct(@PathVariable long id){
        //TODO
        return "";
    }
}
