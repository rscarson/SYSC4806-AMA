package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adambatson on 3/2/2017.
 */
@Controller
public class PostController {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/ama")
    public String amaIndex(Model model) {
        model.addAttribute("amas", postRepo.findAll());
        return "ama/index";
    }

    @GetMapping("/ama/new")
    public String amaForm(Model model) {
        model.addAttribute("ama", new Post());
        return "ama/new";
    }

    @PostMapping("/ama/new")
    public String postNewAma(@RequestParam(value="username") String username, @RequestParam(value="title") String title,
        @RequestParam(value="description") String description, @RequestParam(value="tags") String tags, Model model) {

        User u = new User(username);
        userRepo.save(u);

        Post p = new Post(title, u, description);
        p.setTags(new ArrayList<>(Arrays.asList(tags.split(","))));
        postRepo.save(p);

        model.addAttribute("ama", p);

        return "ama/view";
    }

    @RequestMapping("/ama/view")
    public String viewAMA(@RequestParam(value="id") Long ama, Model model) {
        Post p = postRepo.findOne(ama);
        model.addAttribute("ama", p);
        return "ama/view";
    }

}
