package springsecurity.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springsecurity.bootstrap.service.UserServiceImpl;


import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String getUser(Principal principal, Model model) {
        model.addAttribute("userLogin", userService.getUserByUsername(principal.getName()));
        return "User";
    }
}
