package com.many.miniproject1.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {



    Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    Optional<User> findByEmail(@Param("email") String email);
}
