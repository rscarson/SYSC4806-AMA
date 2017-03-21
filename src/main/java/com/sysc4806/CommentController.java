package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Richard Carson on 3/21/2017.
 */
@Controller
public class CommentController {

    @Autowired
    VoteStatusRepository voteStatusRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping("/comment/up")
    public @ResponseBody int upVote(@RequestParam(value="userID")Long userID, @RequestParam(value="commentID")Long commentID){
        Comment comment = commentRepository.findOne(commentID);
        VoteStatus status = voteStatusRepository.findByCommentIDAndUserIDAndType(commentID, userID, VoteStatus.Vote.Up);
        if (status == null) {
            status = new VoteStatus();
            status.setCommentID(commentID); status.setUserID(userID); status.setType(VoteStatus.Vote.Up);
            voteStatusRepository.save(status);
            comment.upVote();
        } else {
            voteStatusRepository.delete(status);
            comment.downVote();
        }

        return comment.getVotes();
    }
}
