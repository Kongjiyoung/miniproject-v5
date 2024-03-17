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

    //개인 이력서 관리
    @GetMapping("/person/resume")
    public String personResumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
//        List<ResumeResponse.DetailDTO> resumeList = resumeRepository.findresume(sessionUser.getId());
//        System.out.println(resumeList.size());
//
//        ArrayList<ResumeResponse.DetailSkillDTO> resumeSkillList = new ArrayList<>();
//        for (int i = 0; i < resumeList.size(); i++) {
//            List<String> skills = skillRepository.findByResumeId(resumeList.get(i).getId());
//            System.out.println(skills);
//            ResumeResponse.DetailDTO resume = resumeList.get(i);
//            System.out.println(resume);
//
//            resumeSkillList.add(new ResumeResponse.DetailSkillDTO(resume, skills));
//            System.out.println(resumeSkillList.get(i));
//        }
        List<Resume> resumeList = resumeRepository.findPersonId(sessionUser.getId());
        request.setAttribute("resumeList", resumeList);

        return "person/resumes";
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
    public String personSaveResumeForm(ResumeRequest.SaveDTO requestDTO, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/person/loginForm";
        }

        request.setAttribute("person", sessionUser);
        request.setAttribute("resume", requestDTO);
        return "person/saveResumeForm";
    }

    @PostMapping("/person/resume/save")
    public String personSaveResume(ResumeRequest.SaveDTO reqDTO, @RequestParam("skills") List<String> skills) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("reqDTO = " + reqDTO);
        resumeRepository.save(reqDTO.toEntity(sessionUser));

        return "redirect:/person/resume";
    }

    @GetMapping("/person/resume/detail/{id}/updateForm")
    public String personUpdateResumeForm(@PathVariable int id, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/person/loginForm";
        }
        Resume resume = resumeRepository.findByResumeId(id);


        request.setAttribute("resume", resume);
        return "person/updateResumeForm";
    }

    @PostMapping("/person/resume/{id}/detail/update")
    public String personUpdateResume(@PathVariable int id, ResumeRequest.UpdateDTO requestDTO, HttpServletRequest request, @RequestParam("skill") List<String> skills) {

        resumeRepository.update(id, requestDTO);
        // 업데이트된 이력서 정보와 스킬 정보를 반환
        request.setAttribute("resume", requestDTO);
        //request.setAttribute("skills", skills);
        return "redirect:/person/resume/" + id + "/detail";

    }

    @PostMapping("/person/resume/detail/{id}/delete")
    public String personDeletePost(@PathVariable int id, HttpServletRequest request) {
        resumeRepository.deleteById(id);
        return "redirect:/person/resume";
    }

//    //메인 구직 공고
//    @GetMapping("/resume")
//    public String resumeForm() {return "person/main";}
//
//    @GetMapping("/resume/detail/{id}")
//    public String resumeDetailForm(@PathVariable int id) {
//        return "person/resumeDetail";
//    }
//
//    //맞춤 공고 - 기업용
//    @GetMapping("/matching/resume")
//    public String matchingResumeForm() {return "company/matching";}
//
//    @GetMapping("/matching/resume/detail/{id}")
//    public String matchingResumeDetailForm(@PathVariable int id) {
//        return "person/resumeDetail";
//    }
}