package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.User;
import com.example.employee_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user, Model model) {

        String token = userService.verify(user);

        if ("Fail".equals(token)) {
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/login/page"; // Stay on login page if authentication fails
        }
        return "redirect:/dashboard";
    }



    @PostMapping("/user/login")
    public String userLogin(@RequestBody User user) {

        return userService.verify(user);
    }
}
