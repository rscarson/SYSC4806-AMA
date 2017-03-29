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
    VoteStatusRepository voteStatusRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @RequestMapping("/comment/up")
    public @ResponseBody int upVote(@RequestParam(value="userID")long userID, @RequestParam(value="commentID")long commentID){
        return vote(userID, commentID, VoteStatus.Vote.Up);
    }

    @RequestMapping("/comment/down")
    public @ResponseBody int downVote(@RequestParam(value="userID")long userID, @RequestParam(value="commentID")long commentID){
        return vote(userID, commentID, VoteStatus.Vote.Down);
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

        model.addAttribute("title", post.getTitle());
        model.addAttribute("ama", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        return "ama/view";
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

        model.addAttribute("title", post.getTitle());
        model.addAttribute("ama", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        return "ama/view";
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
            Comment c = new Comment();
            c.setPoster(AuthenticationController.CurrentUser());
            c.setContent(content);


            Comment parent = null;
            if (parentID != null) {
                parent = commentRepository.findOne(parentID);
                if (parent == null) {
                    throw new ResourceNotFoundException();
                }

                c.setParent(parent);

                parent.addChild(c);
            } else {
                c.setPost(post);
            }

            commentRepository.save(c);
            if (parent != null) commentRepository.save(parent);
        }

        model.addAttribute("title", post.getTitle());
        model.addAttribute("ama", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        return "ama/view";
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

    /**
     * Vote on a comment
     * @param userID The source user
     * @param commentID The target
     * @param type Type of vote to apply / remove
     * @return New vote count
     */
    private int vote(long userID, long commentID, VoteStatus.Vote type) {
        Comment comment = commentRepository.findOne(commentID);
        User user = userRepository.findOne(userID);

        if (user == null || comment == null) {
            throw new ResourceNotFoundException();
        }

        VoteStatus status = voteStatusRepository.findByCommentAndUserAndType(commentID, userID, type);
        if (status == null) {
            status = new VoteStatus();
            status.setComment(comment); status.setUser(user); status.setType(type);
            voteStatusRepository.save(status);
            if (type == VoteStatus.Vote.Up) comment.upVote(); else comment.downVote();
        } else {
            voteStatusRepository.delete(status);
            if (type == VoteStatus.Vote.Up) comment.downVote(); else comment.upVote();
        }

        return comment.getVotes();
    }
}
