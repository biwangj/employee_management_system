package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.User;
import com.example.employee_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class AddUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             RedirectAttributes redirectAttributes) {
        try {
            System.out.println("User Password: " + password);
            System.out.println("Confirm Password: " + confirmPassword);

            //checks if passwords match in registration
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Password do not match!");
                return "redirect:/register";
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            //creates user if passwords match
            userService.addUser(user);
            return "redirect:/login";

            //checks if username or email already exists in db
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Username already exists!");
            return "redirect:/register";
        }
    }
}

