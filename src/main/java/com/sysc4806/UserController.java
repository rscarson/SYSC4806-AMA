package com.sysc4806;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */
public class UserController {

    @GetMapping("/users")
    public String userIndex(Model model){
        return "users/index";
    }
}
