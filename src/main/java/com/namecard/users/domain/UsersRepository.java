package com.namecard.users.domain;


import com.namecard.users.dto.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<Users, Long>, UsersQuerydslRepository {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByPhoneNum(String phoneNum);

    List<Users> findByEmailOrPhoneNum(String email, String phoneNum);

    Optional<Users> findByUserId(long memberNo);

}
