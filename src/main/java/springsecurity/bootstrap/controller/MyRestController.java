package springsecurity.bootstrap.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springsecurity.bootstrap.entity.User;
import springsecurity.bootstrap.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String BCRYP_TYPE = "$";


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public void addNewUser(@RequestBody User user, Principal principal) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.getUserByUsername(principal.getName()).getPassword().startsWith(BCRYP_TYPE)) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); //У юзера получаем пароль, кодируем его и сетаем в поле пароль обратно
        }
        userService.saveUser(user);
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user, Principal principal) {
        if (!userService.getUserByUsername(principal.getName()).getPassword().startsWith(BCRYP_TYPE)) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); //У юзера получаем пароль, кодируем его и сетаем в поле пароль обратно
        }
        userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}


