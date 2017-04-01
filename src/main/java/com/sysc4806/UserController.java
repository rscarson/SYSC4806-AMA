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

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentRepository commentRepo;

    @RequestMapping("/user/view")
    public String viewUser(@RequestParam(value="id")Long user, Model model){
        User u = userRepo.findOne(user);
        model.addAttribute("posts", postRepo.findByPoster(u));
        model.addAttribute("comments", commentRepo.findByPoster(u));
        if(u == null){
            throw new ResourceNotFoundException();
        }

        model.addAttribute("title", "Overview for " + u.getName());
        model.addAttribute("target", u);
        return "user/view";
    }

    @RequestMapping("/user/follow")
    public String followUser(@RequestParam(value="id")Long user, Model model) {
        User u = userRepo.findOne(user);
        if(u == null){
            throw new ResourceNotFoundException();
        }

        User current = AuthenticationController.CurrentUser();
        current.follow(u); userRepo.save(current);
        model.addAttribute("title", "Overview for " + u.getName());
        model.addAttribute("target", u);
        return "user/view";
    }

    @RequestMapping("/user/unfollow")
    public String unfollowUser(@RequestParam(value="id")Long user, Model model) {
        User u = userRepo.findOne(user);
        if(u == null){
            throw new ResourceNotFoundException();
        }

        User current = AuthenticationController.CurrentUser();
        current.unfollow(u); userRepo.save(current);
        model.addAttribute("title", "Overview for " + u.getName());
        model.addAttribute("target", u);
        return "user/view";
    }
}
