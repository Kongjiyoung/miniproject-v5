package com.many.miniproject1.resume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public List<Resume> findAll() {
        Query query = em.createNativeQuery("select * from resume_tb", Resume.class);

        return query.getResultList();
    }

    public List<Resume> findPersonId(int id) {
        Query query = em.createQuery("select r from Resume r where r.user.id = :id order by r.id desc ", Resume.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Resume findByResumeId(int id) {
        Query query= em.createQuery("select r from Resume r join fetch r.user u where r.id= :id", Resume.class);
        query.setParameter("id", id);
        return (Resume) query.getSingleResult();
    }


    @Transactional
    public void save(Resume resume) {
        em.persist(resume);
    }

    public List<ResumeResponse.DetailDTO> findresume(int u_id) {
        Query query = em.createNativeQuery("SELECT u.email, u.username, u.tel, u.address, u.birth, r.id, r.person_id, r.title, r.profile, r.portfolio, r.introduce, r.career, r.simple_introduce, r.created_at FROM user_tb u INNER JOIN resume_tb r ON u.id = r.person_id where r.person_id=?");
        query.setParameter(1, u_id);

        List<Object[]> results = query.getResultList();
        List<ResumeResponse.DetailDTO> responseDTO = new ArrayList<>();

        for (Object[] result : results) {

            ResumeResponse.DetailDTO DTO = new ResumeResponse.DetailDTO();
            DTO.setEmail((String) result[0]);
            DTO.setUsername((String) result[1]);
            DTO.setTel((String) result[2]);
            DTO.setAddress((String) result[3]);
            DTO.setBirth((String) result[4]);
            DTO.setId((Integer) result[5]);
            DTO.setPersonId((Integer) result[6]);
            DTO.setTitle((String) result[7]);
            DTO.setProfile((String) result[8]);
            DTO.setPortfolio((String) result[9]);
            DTO.setIntroduce((String) result[10]);
            DTO.setCareer((String) result[11]);
            DTO.setSimpleIntroduce((String) result[12]);
            DTO.setCreatedAt((Timestamp) result[13]);

            responseDTO.add(DTO);
        }
        return responseDTO;
    }

    @Transactional
    public void update(int id, ResumeRequest.UpdateDTO reqDTO) {
        Resume resume=findByResumeId(id);
        resume.update(reqDTO);
    }

    public void skilldelete(int id) {
        Query query = em.createNativeQuery("delete from skill_tb where resumeId = ?");
        query.setParameter(1, id);

        query.executeUpdate();
    }

    @Transactional
    public void deleteById(int rid) {
        Query query = em.createQuery("delete from Resume r where r.id = :id");
        query.setParameter("id", rid);

        query.executeUpdate();
    }
}