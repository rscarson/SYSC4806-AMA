package com.sysc4806;

import com.sysc4806.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Richard Carson on 3/21/2017.
 */
@Controller
public class CommentController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @RequestMapping("/comment/up")
    public @ResponseBody VoteParameters upVote(@RequestParam(value="id")long id) {
        Comment comment = commentRepository.findOne(id);
        if (comment == null)
            throw new ResourceNotFoundException();

        comment.upVote(AuthenticationController.CurrentUser());
        commentRepository.save(comment);
        return new VoteParameters(comment);
    }

    @RequestMapping("/comment/down")
    public @ResponseBody VoteParameters downVote(@RequestParam(value="id")long id) {
        Comment comment = commentRepository.findOne(id);
        if (comment == null)
            throw new ResourceNotFoundException();

        comment.downVote(AuthenticationController.CurrentUser());
        commentRepository.save(comment);
        return new VoteParameters(comment);
    }

    @GetMapping("/comment/delete")
    public String postDeleteComment(@RequestParam(value="postID") Long postID, @RequestParam(value="commentID") Long commentID, Model model) {
        Comment comment = commentRepository.findOne(commentID);
        if (comment == null) {
            throw new ResourceNotFoundException();
        }

        comment.setPoster(null);
        comment.setContent("");
        commentRepository.save(comment);

        if (comment.getChildren().size() == 0) {
            if (comment.getParent() != null) {
                comment.getParent().getChildren().removeIf(c -> c.getId() == commentID);
                commentRepository.save(comment.getParent());
            }

            commentRepository.delete(commentID);
        }

        Post post = postRepository.findOne(postID);
        if (post == null) {
            throw new ResourceNotFoundException();
        }

        return "redirect:/ama/view?id="+new Long(post.getId()).toString();
    }

    @PostMapping("/comment/edit")
    public String postEditComment(
            @RequestParam(value="postID") Long postID,
            @RequestParam(value="commentID") Long commentID,
            @RequestParam(value="content") String content,
            Model model) {
        if (content.trim().length() > 0) {
            Comment comment = commentRepository.findOne(commentID);
            if (comment == null) {
                throw new ResourceNotFoundException();
            }

            comment.setContent(content);
            commentRepository.save(comment);
        }

        Post post = postRepository.findOne(postID);
        if (post == null) {
            throw new ResourceNotFoundException();
        }

        return "redirect:/ama/view?id="+new Long(post.getId()).toString();
    }

    @PostMapping("/comment/new")
    public String postNewComment(
            @RequestParam(value="postID") Long postID,
            @RequestParam(value = "parentID", required = false) Long parentID,
            @RequestParam(value="content") String content,
            Model model){
        Post post = postRepository.findOne(postID);
        if (post == null) {
            throw new ResourceNotFoundException();
        }

        if (content.trim().length() > 0) {
            Comment c = new Comment(AuthenticationController.CurrentUser(), content);
            c.setPost(post);

            Comment parent = null;
            if (parentID != null) {
                parent = commentRepository.findOne(parentID);
                if (parent == null) {
                    throw new ResourceNotFoundException();
                }

                c.setParent(parent);
                parent.addChild(c);
            }

            commentRepository.save(c);
            if (parentID != null) commentRepository.save(parent);
        }

        return "redirect:/ama/view?id="+new Long(post.getId()).toString();
    }

    @RequestMapping("comment/view")
    public String viewComment(@RequestParam(value="id") long comment, Model model) {
        Comment c = commentRepository.findOne(comment);
        if (c == null) {
            throw new ResourceNotFoundException();
        }

        model.addAttribute("title", c.getPost().getTitle());
        model.addAttribute("comment", c);
        return "comment/view";
    }
}
