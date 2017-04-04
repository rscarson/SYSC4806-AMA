package com.sysc4806;

import com.sysc4806.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String viewUser(@RequestParam(value="id")Long user, @RequestParam(value="sortby", required = false)
            String sortby, Model model){
        User u = userRepo.findOne(user);
        List<Post> posts;
        List<Comment> comments;

        if(sortby != null) { //Sort the comments
            switch(sortby) {
                case "upvotes":
                    comments = commentRepo.findByPosterOrderByVotesDesc(u);
                    posts = postRepo.findByPosterOrderByVotesDesc(u);
                    break;
                case "newest":
                    comments = commentRepo.findByPosterOrderByCreatedDesc(u);
                    posts = postRepo.findByPosterOrderByCreatedDesc(u);
                    break;
                case "oldest":
                    comments = commentRepo.findByPosterOrderByCreatedAsc(u);
                    posts = postRepo.findByPosterOrderByCreatedAsc(u);
                    break;
                case "downvotes":
                    comments = commentRepo.findByPosterOrderByVotesAsc(u);
                    posts = postRepo.findByPosterOrderByVotesAsc(u);
                    break;
                default:
                    comments = commentRepo.findByPoster(u);
                    posts = postRepo.findByPoster(u);
                    break;
            }
        } else {
            comments = commentRepo.findByPoster(u);
            posts = postRepo.findByPoster(u);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
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
