package com.many.miniproject1.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeJPARepository extends JpaRepository<Resume, Integer> {

    @Query("select r.id, r.title, r.profile, r.career, r.simpleIntroduce from Resume r join r.user u where u.id=:id")
    List<ResumeRequest.ListDTO> findByPersonId(@Param("id") int id);

}
