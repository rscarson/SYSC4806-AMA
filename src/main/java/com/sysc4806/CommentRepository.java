package com.sysc4806;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Richard on 3/21/2017.
 */
@Repository
public interface CommentRepository  extends CrudRepository<Comment, Long> {
    public List<Comment> findByPostAndParent(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByVotesAsc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByVotesDesc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByCreatedDesc(Post post, Comment parent);

    public List<Comment> findByPostAndParentOrderByCreatedAsc(Post post, Comment parent);

    public List<Comment> findByPoster(User poster);

    public List<Comment> findByPosterOrderByVotesAsc(User poster);

    public List<Comment> findByPosterOrderByVotesDesc(User poster);

    public List<Comment> findByPosterOrderByCreatedDesc(User poster);

    public List<Comment> findByPosterOrderByCreatedAsc(User poster);

}
