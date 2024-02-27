package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        try {
            session.removeAttribute("message");
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                session.setAttribute("message", "이미 등록된 이메일입니다!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                session.setAttribute("message", "회원가입 완료!!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                session.setAttribute("message", "비밀번호가 일치하지 않습니다");
                System.out.println("password not same");
                return "redirect:/register";
            }


        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "에러 !! 잠시 후 다시 시도해주세요");
        }
        return "register";
    }
}
