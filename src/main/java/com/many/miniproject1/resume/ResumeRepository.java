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
    public Integer save(ResumeRequest.SaveDTO requestDTO, String profileFileName) {
        String q = """
                      insert into resume_tb(person_id, title,  profile, portfolio, introduce, career,
                      simple_introduce, created_at) 
                      values (?, ?, ?, ?, ?, ?, ?, now());
                """;

        Query query = em.createNativeQuery(q);

        query.setParameter(1, requestDTO.getPersonId());
        query.setParameter(2, requestDTO.getTitle());

        query.setParameter(3, profileFileName);

        query.setParameter(4, requestDTO.getPortfolio());
        query.setParameter(5, requestDTO.getIntroduce());
        query.setParameter(6, requestDTO.getCareer());
        query.setParameter(7, requestDTO.getSimpleIntroduce());

//        query.setParameter(8, requestDTO.getSkill());


        query.executeUpdate();

        // max pk 받아서 리턴!!
        Query maxQquery = em.createNativeQuery("select max(id) from resume_tb");
        Integer resumeId = (Integer) maxQquery.getSingleResult();
        return resumeId;

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
    public void update(int id, ResumeRequest.UpdateDTO requestDTO, String profileFileName) {
        Query query = em.createNativeQuery("update resume_tb set title=?, profile=?, portfolio=?, introduce=?, career=?, simple_introduce=? where id = ?");
        System.out.println(requestDTO.getTitle());
        System.out.println(profileFileName);
        System.out.println(requestDTO.getPortfolio());
        System.out.println(requestDTO.getIntroduce());
        System.out.println(requestDTO.getCareer());
        System.out.println(requestDTO.getSimpleIntroduce());
        System.out.println(id);
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, profileFileName);
        query.setParameter(3, requestDTO.getPortfolio());
        query.setParameter(4, requestDTO.getIntroduce());
        query.setParameter(5, requestDTO.getCareer());
        query.setParameter(6, requestDTO.getSimpleIntroduce());
        query.setParameter(7, id);

        query.executeUpdate();

    }

    public void skilldelete(int id) {
        Query query = em.createNativeQuery("delete from skill_tb where resumeId = ?");
        query.setParameter(1, id);

        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        Query query = em.createNativeQuery("delete from resume_tb where id = ?");
        query.setParameter(1, id);

        query.executeUpdate();
    }
}