package com.namecard.member.domain;


import com.namecard.member.dto.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByPhoneNum(String phoneNum);

    List<Users> findByEmailOrPhoneNum(String email, String phoneNum);
    Optional<Users> findByUserId(long memberNo);

}
