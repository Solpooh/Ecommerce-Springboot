package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
            CustomerDto customerDtoSave = customerService.save(customerDto);
            attributes.addFlashAttribute("success", "회원가입 성공!!");
        }
        return "register";
    }
}
