package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/users")
    public String userIndex(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "users/index";
    }

    @GetMapping("/users/new")
    public String userForm(Model model){
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping("/users/new")
    public String postNewUser(@RequestParam(value="userName") String userName, Model model){
        User u = new User(userName);
        userRepo.save(u);
        model.addAttribute("user", u);
        return "user/view";
    }

    @RequestMapping("/users/view")
    public String viewUser(@RequestParam(value="id")Long user, Model model){
        User u = userRepo.findOne(user);
        model.addAttribute("user", u);
        return "user/view";
    }
}
