package com.many.miniproject1.resume;

import com.many.miniproject1.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.List;

public class ResumeRequest {

    @Data
    public static class SaveDTO{
        private Integer id;
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;

        public Resume toEntity(User user){
            return Resume.builder()
                    .id(id)
                    .title(title)
                    .profile(profile)
                    .portfolio(portfolio)
                    .introduce(introduce)
                    .career(career)
                    .simpleIntroduce(simpleIntroduce)
                    .user(user)
                    .build();
        }

    }

    @Data
    public static class ResumeDTO {
        private Integer id;
        private Integer personId;
        private String title;
        private MultipartFile profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
        private String email;
        private String password;
        private String username;
        private String tel;
        private String address;
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
    }

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String profile;
        private String career;
        private String simpleIntroduce;
    }
}