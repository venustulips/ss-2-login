package com.example.ss2login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(value = {"/admin/login", "/"})
    public String adminLoginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/home")
    public String adminHomePage() {
        return "admin/home";
    }

    @GetMapping("/user/login")
    public String userLoginPage() {
        return "user/login";
    }

    @GetMapping("/user/home")
    public String userHomePage() {
        return "user/home";
    }

}