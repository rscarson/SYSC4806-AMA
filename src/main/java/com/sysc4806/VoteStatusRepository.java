package com.sysc4806;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Richard on 3/21/2017.
 */
public interface VoteStatusRepository  extends CrudRepository<VoteStatus, Long> {
    public VoteStatus findByCommentAndUser(long commentID, long userID);
    public VoteStatus findByCommentAndUserAndType(long commentID, long userID, VoteStatus.Vote type);
}