package com.many.miniproject1.User;

import com.many.miniproject1.user.User;
import com.many.miniproject1.user.UserRepository;
import com.many.miniproject1.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailAndPassword_test(){
        // given
        String email = "captain_kong@nate.com";
        String password = "1234";
        UserRequest.LoginDTO reqDTO=new UserRequest.LoginDTO();
        reqDTO.setEmail(email);
        reqDTO.setPassword(password);
        // when
        User user=userRepository.findByEmailAndPassword(reqDTO);
        // then
        System.out.println(user);
    }
}
