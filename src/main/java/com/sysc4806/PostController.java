package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sysc4806.exceptions.ResourceNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by adambatson on 3/2/2017.
 */
@Controller
public class PostController {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CommentRepository commentRepo;

    @RequestMapping({"/"})
    public String amaIndex(Model model) {
        model.addAttribute("amas", postRepo.findAll());
        model.addAttribute("page", "index");
        model.addAttribute("title", "All AMAs");
        return "ama/index";
    }

    @RequestMapping("/following")
    public String following(Model model) {
        List<Post> posts = postRepo.findByPoster(AuthenticationController.CurrentUser());
        model.addAttribute("amas", posts);
        model.addAttribute("page", "following");
        model.addAttribute("title", "AMAs By Those I Follow");
        return "ama/following";
    }

    @GetMapping("/ama/new")
    public String amaForm(Model model) {
        model.addAttribute("ama", new Post());
        model.addAttribute("page", "new");
        model.addAttribute("title", "New Post");
        return "ama/new";
    }

    @PostMapping("/ama/new")
    public String postNewAma(@RequestParam(value="title") String title,
                             @RequestParam(value="description") String description,
                             @RequestParam(value="tags") String tags, Model model) {

        Post p = new Post(title, AuthenticationController.CurrentUser(), description);
        ArrayList<String> tagsTrimmed = new ArrayList<>();
        for(String s : tags.split(","))
            tagsTrimmed.add(s.trim());
        p.setTags(tagsTrimmed);
        postRepo.save(p);

        model.addAttribute("ama", p);
        model.addAttribute("title", p.getTitle());
        model.addAttribute("page", "view");
        model.addAttribute("comments", commentRepo.findByPost(p));

        return "ama/view";
    }

    @RequestMapping("/ama/view")
    public String viewAMA(@RequestParam(value="id") Long ama, Model model) {
        Post p = postRepo.findOne(ama);
        if (p == null) {
            throw new ResourceNotFoundException();
        } else {
            model.addAttribute("ama", p);
            model.addAttribute("title", p.getTitle());
            model.addAttribute("page", "view");
            model.addAttribute("comments", commentRepo.findByPost(p));
            return "ama/view";
        }
    }
}