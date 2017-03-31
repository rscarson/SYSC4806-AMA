package com.sysc4806;

/**
 * Created by Richard on 3/30/2017.
 */
public class VoteParameters {
    private final int votes;
    private final boolean up;

    private final boolean dn;

    public VoteParameters(int votes, boolean up, boolean dn) {
        this.votes = votes;
        this.up = up;
        this.dn = dn;
    }

    public VoteParameters(Post post) {
        this(
                post.getVotes(),
                post.hasUpVoted(AuthenticationController.CurrentUser()),
                post.hasDownVoted(AuthenticationController.CurrentUser()));
    }

    public VoteParameters(Comment comment) {
        this(
                comment.getVotes(),
                comment.hasUpVoted(AuthenticationController.CurrentUser()),
                comment.hasDownVoted(AuthenticationController.CurrentUser()));
    }

    public int getVotes() { return votes; }
    public boolean isUp() { return up; }
    public boolean isDn() { return dn; }
}
