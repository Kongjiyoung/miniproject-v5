package com.many.miniproject1.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final Environment env;
    private final UserFileService userFileService;
    private final UserService userService;
    // 회사 회원가입
    @GetMapping("/company/joinForm")
    public String companyJoinForm() {
        return "company/joinForm";
    }

    @PostMapping("/company/join")
    public String companyJoin(UserRequest.JoinDTO requestDTO, HttpServletRequest request) {

        requestDTO.setRole("company");
        userRepository.personSave(requestDTO.toEntity());
        return "redirect:/company/loginForm";
    }

    // 회사 로그인
    @GetMapping("/company/loginForm")
    public String companyLoginForm() {
        return "company/loginForm";
    }

    @PostMapping("/company/login")
    public String companyLogin(UserRequest.LoginDTO requestDTO) {
        User sessionUser = userRepository.findByEmailAndPassword(requestDTO);

        if(sessionUser==null){
            return "redirect:/company/loginForm";
        }
        if (sessionUser.getRole().equals("person")){
            return "redirect:/person/loginForm";
        }

        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/company/main";
    }


    // 개인 로그인 회원가입
    @GetMapping("/person/joinForm")
    public String personJoinForm() {
        return "person/joinForm";
    }

    @PostMapping("/person/join")
    public String personJoin(UserRequest.JoinDTO reqDTO) {
        userService.회원가입(reqDTO);
        return "redirect:/person/loginForm";
    }

    // 개인 로그인 회원가입
    @GetMapping("/person/loginForm")
    public String personLoginForm() {
        return "person/loginForm";
    }

    @PostMapping("/person/login")
    public String personLogin(UserRequest.LoginDTO requestDTO) {

        User sessionUser = userRepository.findByEmailAndPassword(requestDTO);

        if(sessionUser==null){
            return "redirect:/person/loginForm";
        }
        if (sessionUser.getRole().equals("company")){
            return "redirect:/company/loginForm";
        }

        session.setAttribute("sessionUser", sessionUser);

        return "redirect:/person/main";
    }


    //기업 개인 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }


    //회사 정보 및 수정
    //회사 정보 수정
    @GetMapping("/company/info")
    public String companyInfo(HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "/company/loginForm";
        }
        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);
        return "company/companyInfo";
    }

    @GetMapping("/company/info/updateForm")
    public String companyInfoUpdateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // sessionUser가 null인 경우, 로그인 페이지로 리다이렉트
            return "redirect:/company/loginForm";
        }
        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);
        return "company/updateInfoForm";
    }

    @PostMapping("/company/info/update")
    public String companyInfoUpdate(UserRequest.CompanyUpdateDTO requestDTO, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 프로필 저장
        MultipartFile profile = requestDTO.getProfile();
        String profileFilename = UUID.randomUUID() + "_" + profile.getOriginalFilename();
        Path profilePath = Paths.get("./images/" + profileFilename);
        try {
            Files.write(profilePath, profile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);

        // 새 비밀번호가 비어있으면 기존 비밀번호를 사용하도록 설정
        if (StringUtils.isEmpty(requestDTO.getNewPassword())) {
            requestDTO.setNewPassword(user.getPassword());
        }

        // requestDTO.setProfilePath(profilePath);
        userRepository.companyUpdate(requestDTO, sessionUser.getId(), profileFilename);

        System.out.println(requestDTO);
        return "redirect:/company/info";
    }

    //개인 프로필 정보 및 수정
    @GetMapping("/person/info")
    public String personal(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // sessionUser가 null인 경우, 로그인 페이지로 리다이렉트
            return "/person/loginForm";
        }
        User user = userRepository.findById(sessionUser.getId());

        request.setAttribute("user", user);

        return "person/personalInfo";
    }

    @GetMapping("/person/info/updateForm")
    public String personInfoInfoUpdateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);
        return "person/updatePersonalForm";
    }

    @PostMapping("/person/info/update")
    public String personInfoUpdate(UserRequest.PersonUpdateDTO reqDTO, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);

        User updateUser = userRepository.Updateperson(sessionUser.getId(),reqDTO);
        session.setAttribute("sessionUser", updateUser);

        return "redirect:/person/info";
    }
}