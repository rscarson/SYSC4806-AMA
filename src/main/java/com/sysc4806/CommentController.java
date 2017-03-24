package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/comment/new")
    public String postNewComment(@RequestParam(value="postID") long postID, @RequestParam(value="content") String content, Model model){
        Post parent = postRepository.findOne(postID);
        Comment c = new Comment();
        c.setPoster(AuthenticationController.CurrentUser());
        c.setContent(content);
        c.setPost(parent);
        commentRepository.save(c);

        model.addAttribute("title", parent.getTitle());
        model.addAttribute("ama", parent);
        model.addAttribute("comments", commentRepository.findByPost(parent));
        return "ama/view";
    }

    @PostMapping("/comment/edit")
    public String postEdit(@RequestParam(value="commentID") long commentID, @RequestParam(value="content") String content, Model model){
        Comment c = commentRepository.findOne(commentID);
        c.setContent(content);
        commentRepository.save(c);

        model.addAttribute("title", c.getPost().getTitle());
        model.addAttribute("comment", c);
        return "comment/view";
    }

    @RequestMapping("comment/view")
    public String viewComment(@RequestParam(value="id") long comment, Model model) {
        Comment c = commentRepository.findOne(comment);
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
