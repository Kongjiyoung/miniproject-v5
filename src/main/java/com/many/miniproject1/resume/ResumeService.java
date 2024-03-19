package com.many.miniproject1.resume;

import com.many.miniproject1._core.errors.exception.Exception403;
import com.many.miniproject1._core.errors.exception.Exception404;
import com.many.miniproject1.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeJPARepository resumeJPARepository;

    public void 이력서쓰기(ResumeRequest.SaveDTO reqDTO, User sessionUser){
        resumeJPARepository.save(reqDTO.toEntity(sessionUser));
    }

    public Resume 이력서조회(int id){
        Resume resume=resumeJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("이력서를 조회할 수 없습니다"));

        return resume;
    }

    public Resume 이력서수정(int resumeId, ResumeRequest.UpdateDTO reqDTO, User sessionUser){
        Resume resume=resumeJPARepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 조회할 수 없습니다"));

        if(sessionUser.getId()!=resume.getUser().getId()){
            throw new Exception403("이력서를 수정할 권한이 없습니다");
        }
        resume.update(reqDTO);
        return resume;
    }

    public void 이력서삭제(int resumeId, int sessionUserId) {
        Resume resume=resumeJPARepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 조회할 수 없습니다"));

        if(sessionUserId!=resume.getUser().getId()){
            throw new Exception403("이력서를 수정할 권한이 없습니다");
        }

        resumeJPARepository.deleteById(resumeId);

    }

    public List<ResumeRequest.ListDTO> 이력서목록보기(int sessionUserId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ResumeRequest.ListDTO> resumeList= resumeJPARepository.findByPersonId(sessionUserId);

        return resumeList;
    }
}
