package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/account/product")
public class AccountProductController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/list")
    public String ListProducts(@RequestParam boolean active, ModelMap model){
        model.put("products",productService.GetProductsByActive(active));
        return "account_manager_product_list";
    }

    @RequestMapping("/{id}/view")
    public String ViewProduct(ModelMap model, @PathVariable long id){
        model.put("product", productService.GetProductById(id));
        return "account_manager_product_view";
    }
}
