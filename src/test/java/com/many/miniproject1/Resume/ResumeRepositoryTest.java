package com.many.miniproject1.Resume;

import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ResumeRepository.class)
@DataJpaTest
public class ResumeRepositoryTest {
    @Autowired
    private  ResumeRepository resumeRepository;

    @Test
    public void findPersonId_Test(){
        //given
        int id=1;
        //when
        List<Resume> resumeList= resumeRepository.findPersonId(id);
        //then
        for (Resume resume : resumeList){
            System.out.println(resume);
        }
        assertThat(resumeList.size()).isEqualTo(2);
    }

    @Test
    public void findById_test(){
        //given
        int id =1;
        //when
        Resume resume= resumeRepository.findByResumeId(id);
        //then
        System.out.println(resume);
        assertThat(resume.getId()).isEqualTo(1);
    }

}
