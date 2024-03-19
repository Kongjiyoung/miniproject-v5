package com.many.miniproject1.user;

import com.many.miniproject1._core.errors.exception.Exception400;
import com.many.miniproject1._core.errors.exception.Exception401;
import com.many.miniproject1._core.errors.exception.Exception404;
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

    public User 로그인(UserRequest.LoginDTO reqDTO){
        User sessionUser=userJPARepository.findByEmailAndPassword(reqDTO.getEmail(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다"));

        return sessionUser;
    }

    public User 회원조회(int id){
       User user= userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));
        return user;
    }

    public User 회원수정(int id, UserRequest.PersonUpdateDTO reqDTO){
        User user= userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        user.personUpdate(reqDTO);
        return user;
    }
}
