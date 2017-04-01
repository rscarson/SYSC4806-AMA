package com.sysc4806;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Richard on 3/21/2017.
 */
public interface CommentRepository  extends CrudRepository<Comment, Long> {
    public List<Comment> findByPostAndParent(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByVotesAsc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByVotesDesc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByCreatedDesc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByCreatedAsc(Post post, Comment parent);

}
