package com.sysc4806;

import com.sysc4806.exceptions.ResourceNotFoundException;
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

    @RequestMapping("/user")
    public String userIndex(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "user/index";
    }

    @GetMapping("/user/new")
    public String userForm(Model model){
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping("/user/new")
    public String postNewUser(@RequestParam(value="username") String userName, Model model){
        User u = new User(userName);
        userRepo.save(u);
        model.addAttribute("user", u);
        return "user/view";
    }

    @RequestMapping("/user/view")
    public String viewUser(@RequestParam(value="id")Long user, Model model){
        User u = userRepo.findOne(user);
        if(u == null){
            throw new ResourceNotFoundException();
        }
        else{
            model.addAttribute("user", u);
            return "user/view";
        }
    }
}
