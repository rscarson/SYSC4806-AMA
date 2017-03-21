package com.sysc4806;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Richard on 3/21/2017.
 */
public interface CommentRepository  extends CrudRepository<Comment, Long> {
}
