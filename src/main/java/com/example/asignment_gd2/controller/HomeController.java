package com.example.asignment_gd2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showMain(Model model, HttpSession session){
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập
        }
        return "home";
    }

}
