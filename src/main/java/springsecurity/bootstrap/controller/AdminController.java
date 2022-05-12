package springsecurity.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.bootstrap.entity.User;
import springsecurity.bootstrap.service.RoleService;
import springsecurity.bootstrap.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;  //Чтобы не создавать объект используем Di
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String BCRYP_TYPE = "$";

    @GetMapping
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("userLogin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("newUser", new User());
        return "Admin";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user, Principal principal) {
        if (!userService.getUserByUsername(principal.getName()).getPassword().startsWith(BCRYP_TYPE)) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); //У юзера получаем пароль, кодируем его и сетаем в поле пароль обратно
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
