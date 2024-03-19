package com.many.miniproject1.resume;

import com.many.miniproject1.skill.Skill;
import com.many.miniproject1.skill.SkillRepository;
import com.many.miniproject1.skill.SkillRequest;
import com.many.miniproject1.user.User;
import com.many.miniproject1.user.UserFileService;
import com.many.miniproject1.user.UserRepository;
import com.many.miniproject1.user.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ResumeController{
    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;
    private final HttpSession session;
    private final UserFileService userFileService;
    private final UserRepository userRepository;
    private final ResumeService resumeService;

    //개인 이력서 관리
    @GetMapping("/person/resume")
    public String personResumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ResumeRequest.ListDTO> resumeList = resumeService.이력서목록보기(sessionUser.getId());
        request.setAttribute("resumeList", resumeList);
        return "person/resumes";
    }
    @PostMapping("/person/resume/detail/{id}/delete")
    public String resumeDelete(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서삭제(id, sessionUser.getId());
        return "redirect:/person/resume";
    }

    @GetMapping("/person/resume/detail/{id}/updateForm")
    public String personUpdateResumeForm(@PathVariable int id, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/person/loginForm";
        }
        Resume resume = resumeService.이력서조회(id);
        request.setAttribute("resume", resume);
        return "person/updateResumeForm";
    }

    @PostMapping("/person/resume/{id}/detail/update")
    public String personUpdateResume(@PathVariable int id, ResumeRequest.UpdateDTO reqDTO, @RequestParam("skill") List<String> skills) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        resumeService.이력서수정(id, reqDTO, sessionUser);
        return "redirect:/person/resume/" + id + "/detail";

    }

    @PostMapping("/person/resume/save")
    public String personSaveResume(ResumeRequest.SaveDTO reqDTO, @RequestParam("skills") List<String> skills) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        resumeService.이력서쓰기(reqDTO, sessionUser);

        return "redirect:/person/resume";
    }



    @GetMapping("/person/resume/{id}/detail")
    public String personResumeDetailForm(@PathVariable int id, HttpServletRequest request) {
        System.out.println("id: " + id);

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/person/loginForm";
        }
        Resume resume=resumeRepository.findByResumeId(id);
        request.setAttribute("resume", resume);


        return "person/resumeDetail";
    }

    @GetMapping("/person/resume/saveForm")
    public String personSaveResumeForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/person/loginForm";
        }
        return "person/saveResumeForm";
    }





}