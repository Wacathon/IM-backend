package com.namecard.member.repository;


import com.namecard.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydslRepository {

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m JOIN FETCH m.card c WHERE c.phoneNumber = :phoneNumber")
    Optional<Member> findByPhoneNum(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT m FROM Member m JOIN FETCH m.card c WHERE  m.email = :email OR c.phoneNumber = :phoneNumber")
    List<Member> findByEmailOrPhoneNum(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    Optional<Member> findByMemberId(long memberNo);

}
