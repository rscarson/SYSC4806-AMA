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

    @RequestMapping("/user/view")
    public String viewUser(@RequestParam(value="id")Long user, Model model){
        User u = userRepo.findOne(user);
        if(u == null){
            throw new ResourceNotFoundException();
        }
        else{
            model.addAttribute("title", "Overview for " + AuthenticationController.CurrentUser().getName());
            model.addAttribute("user", u);
            return "user/view";
        }
    }
}
