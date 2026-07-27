package com.lesson.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute Admin admin, Model model) {

        if (admin.getEmail() == null || admin.getEmail().isEmpty() ||
            admin.getPassword() == null || admin.getPassword().isEmpty()) {

            model.addAttribute("error", "メールアドレスとパスワードは必須です");
            return "admin/signup";
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepository.save(admin);

        return "redirect:/admin/login";
    }

    
    @GetMapping("/login")
    public String loginForm() {
        return "admin/login";
    }

}
