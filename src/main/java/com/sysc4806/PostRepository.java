package com.sysc4806;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by richardcarson3 on 3/2/2017.
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByPoster(User poster);

    List<Post> findByPosterIsNotNullAndCompleted(boolean completed);

    List<Post> findByPosterOrderByVotesAsc(User poster);

    List<Post> findByPosterOrderByVotesDesc(User poster);

    List<Post> findByPosterOrderByCreatedAsc(User poster);

    List<Post> findByPosterOrderByCreatedDesc(User poster);

    List<Post> findByPosterIsNotNull();
}