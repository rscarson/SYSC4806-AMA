package com.sysc4806;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Richard on 3/21/2017.
 */
@Entity
public class VoteStatus {
    @Id
    @GeneratedValue
    private long id;

    private Vote type;

    @ManyToOne
    private long commentID;

    @ManyToOne
    private long userID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Vote getType() {
        return type;
    }

    public void setType(Vote type) {
        this.type = type;
    }

    public enum Vote {
        None, Up, Down
    }
}
