package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/account/product")
public class AccountProductController {
    @RequestMapping("/list")
    public String ListProducts(){

    }

    @RequestMapping("/{id}/view")
    public String ViewProduct(){

    }
}
