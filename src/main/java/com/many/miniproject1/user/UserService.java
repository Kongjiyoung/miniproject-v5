package com.many.miniproject1.user;

import com.many.miniproject1._core.errors.exception.Exception400;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserJPARepository userJPARepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO reqDTO){
        reqDTO.setRole("person");
        Optional<User> userOP=userJPARepository.findByEmail(reqDTO.getEmail());

        if(userOP.isPresent()){
            throw new Exception400("중복된 이메일입니다");
        }
        userJPARepository.save(reqDTO.toEntity());
    }
}
