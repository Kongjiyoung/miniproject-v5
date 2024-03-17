package com.many.miniproject1.User;

import com.many.miniproject1.user.User;
import com.many.miniproject1.user.UserRepository;
import com.many.miniproject1.user.UserRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;
    @Test
    public void updatePerson_test(){
        // given
        int id =1;
        UserRequest.PersonUpdateDTO reqDTO=new UserRequest.PersonUpdateDTO();
        String tel="010-1111-1111";
        reqDTO.setTel(tel);
        // when
        User user=userRepository.Updateperson(id,reqDTO);
        em.flush();
        // then
        assertThat(userRepository.findById(id).getTel()).isEqualTo("010");
    }

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
    
    @Test
    public void findById_test(){
        // given
        int id=1;
        
        // when
        User user=userRepository.findById(id);
        
        // then
        System.out.println("user = " + user);
    }
}
