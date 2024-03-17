package com.many.miniproject1.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {
    @Data
    public static class JoinDTO{
        private String profile;
        private String role;
        private String email;
        private String password;
        private String username;
        private String companyName;
        private String companyNum;
        private String address;
        private String birth;
        private String tel;

        public User toEntity(){
            return User.builder()
                    .profile(profile)
                    .role(role)
                    .email(email)
                    .password(password)
                    .username(username)
                    .companyName(companyName)
                    .companyNum(companyNum)
                    .address(address)
                    .birth(birth)
                    .tel(tel)
                    .build();
        }

    }

    @Data
    public class SaveDTO {
        private Integer id;
    }

    @Data
    public static class CompanyUpdateDTO {
        private Integer id;
        private MultipartFile profile;
        private String companyName;
        private String companyNum;
        private String address;
        private String email;
        private String password;
        private String username;
        private String tel;
        private String newPassword;
    }
    @Data
    public static class PersonUpdateDTO {
        private String profile;
        private String address;
        private String tel;
        private String password;
    }

    @Data
    public static class LoginDTO {
        private String email;
        private String password;
    }


}