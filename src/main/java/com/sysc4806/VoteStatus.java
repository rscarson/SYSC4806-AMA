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
    private Comment comment;

    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vote getType() {
        return type;
    }

    public void setType(Vote type) {
        this.type = type;
    }

    public enum Vote {
        Up, Down
    }
}
