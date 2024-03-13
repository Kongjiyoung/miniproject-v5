package com.many.miniproject1;

import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ResumeRepository.class)
@DataJpaTest
public class ResumeRepositoryTest {
    @Autowired
    private  ResumeRepository resumeRepository;

    @Test
    public void findById_test(){
        //given
        int id =1;
        //when
        Resume resume= resumeRepository.findById(id);
        //then
        System.out.println(resume);
        assertThat(resume.getId()).isEqualTo(1);
    }

}
