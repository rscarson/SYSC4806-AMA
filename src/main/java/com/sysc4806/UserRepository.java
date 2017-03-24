package com.sysc4806;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by richardcarson3 on 3/2/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}