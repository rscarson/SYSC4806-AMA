package com.sysc4806;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by adambatson on 3/2/2017.
 */
@Controller
public class PostController {

    @RequestMapping("/ama")
    public String amaIndex(Model model) {
        return "ama/index";
    }

    @GetMapping("/ama/new")
    public String amaForm(Model model) {
        return "ama/new";
    }

    @PostMapping("/ama/new")
    public String postNewAma(Model model) {
        return "view";
    }

}
