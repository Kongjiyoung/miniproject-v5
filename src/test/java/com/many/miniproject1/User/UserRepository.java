package com.many.miniproject1.User;

import com.many.miniproject1.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepository {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailAndPassword_test(){
        // given
        String email = "captain_kong@nate.com";
        String password = "1234";
        // when
        userRepository.fi

        // then

    }
}
