package com.sysc4806;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adambatson on 3/20/2017.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Post parent;
    @OneToMany
    private List<Comment> children;
    @OneToOne
    private Post post;
    private String content;
    private int votes;

    public Comment(Post parent, String content) {
        this();
        this.parent = parent;
        this.content = content;
    }

    public Comment(String content) {
        this(null, content);
    }

    public Comment() {
        children = new ArrayList<>();
        votes = 0;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setParent(Post parent) { this.parent = parent; }

    public Post getParent() { return parent; }

    public void addChild(Comment child) { children.add(child); }

    public List<Comment> getChildren() { return children; }

    public void setContent(String content) { this.content = content; }

    public String getContent() { return content; }

    public Post getPost() { return post; }

    public void setPost(Post post) { this.post = post; }

    public int getVotes() { return votes; }

    public void upVote() { votes ++; }

    public void downVote() { votes --; }

    /*
    public ArrayList<Comment> getAncestory() {
        ArrayList<Post> parents = new ArrayList<>();
        Comment curr = this;
        while(curr.parent != null) {
            parents.add(curr.parent);
            curr = curr.parent;
        }
        return parents;
    }
    */


}
