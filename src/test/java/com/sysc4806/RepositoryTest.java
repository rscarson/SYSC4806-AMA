package com.sysc4806;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by richardcarson3 on 3/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired PostRepository postRepository;
    @Autowired UserRepository userRepository;

    @Before
    public void setUp() {}

    @Test
    public void TestPostAndUserRepository() {
        User user = new User();
        userRepository.save(user);

        assert(userRepository.findAll().iterator().hasNext());

        Post post = new Post("title", user, "description");
        postRepository.save(post);

        assert(postRepository.findAll().iterator().hasNext());
    }
}
