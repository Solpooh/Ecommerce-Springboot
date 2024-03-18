package com.ecommerce.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AccountController {
    @GetMapping("/account")
    public String accountHome(Model model, Principal principal) {
        return "account";
    }
}
