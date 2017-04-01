package com.sysc4806;

import com.ocpsoft.pretty.time.PrettyTime;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> children;

    @ManyToOne
    private Post post;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> upVoters;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> dnVoters;

    @ManyToOne
    private Comment parent;

    @Column(columnDefinition = "TEXT")
    private String source;

    @Column(columnDefinition = "TEXT")
    private String content;
    private int votes;

    public Comment(Post post, String content) {
        this();
        setPost(post);
        setContent(content);
    }

    public Comment(User poster, String content) {
        this();
        setPoster(poster);
        setContent(content);
        upVote(AuthenticationController.CurrentUser());
    }

    public Comment() {
        children = new ArrayList<>();
        upVoters = new HashSet<>();
        dnVoters = new HashSet<>();
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

    public Comment getParent() { return parent; }
    public void setParent(Comment parent) { this.parent = parent; }

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

    public String getContent() { return content; }
    public String getSource() { return source; }
    public void setContent(String content) {
        this.source = content;
        this.content = MarkdownTranslator.translate(content);
    }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public boolean hasUpVoted(User u) { return u != null && upVoters.stream().filter(v -> v.getId() == u.getId()).collect(Collectors.toList()).size() > 0; }
    public void clearUpVotes(User u) {
        Iterator<User> i = upVoters.iterator();
        while (i.hasNext()) {
            User f = i.next();
            if (f.getId() == u.getId()) {
                i.remove();
                votes --;
            }
        }
    }
    public void upVote(User u) {
        if (u == null || poster == null)
            return;

        clearDownVotes(u);
        if (hasUpVoted(u)) {
            clearUpVotes(u);
        } else {
            upVoters.add(u);
            votes ++;
        }
    }

    public boolean hasDownVoted(User u) { return u != null && dnVoters.stream().filter(v -> v.getId() == u.getId()).collect(Collectors.toList()).size() > 0; }
    public void clearDownVotes(User u) {
        Iterator<User> i = dnVoters.iterator();
        while (i.hasNext()) {
            User f = i.next();
            if (f.getId() == u.getId()) {
                i.remove();
                votes ++;
            }
        }
    }
    public void downVote(User u) {
        if (u == null)
            return;

        clearUpVotes(u);
        if (hasDownVoted(u)) {
            clearDownVotes(u);
        } else {
            dnVoters.add(u);
            votes --;
        }
    }

    public int getVotes() { return votes; }
}
