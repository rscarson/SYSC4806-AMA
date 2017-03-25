package com.sysc4806;

import com.ocpsoft.pretty.time.PrettyTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by adambatson on 3/20/2017.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    private Date created;
    private Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    @ManyToOne
    private User poster;

    @OneToMany
    private List<Comment> children;

    @ManyToOne
    private Post post;

    private String content;
    private int votes;

    public Comment(Post post, String content) {
        this();
        this.content = content;
    }

    public Comment() {
        children = new ArrayList<>();
        votes = 0;
    }

    public Date getUpdated() { return updated; }
    public void setUpdated(Date updated) { this.updated = updated; }
    public String getFormattedUpdated() {
        PrettyTime p = new PrettyTime();
        return p.format(updated);
    }

    public Date getCreated() { return created; }
    public String getFormattedCreated() {
        PrettyTime p = new PrettyTime();
        return p.format(created);
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public User getPoster() { return poster; }
    public void setPoster(User poster) { this.poster = poster; }

    public void addChild(Comment child) { children.add(child); }
    public List<Comment> getChildren() { return children; }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public int getVotes() { return votes; }
    public void upVote() { votes ++; }
    public void downVote() { votes --; }
}
