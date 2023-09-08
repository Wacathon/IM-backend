package com.namecard.member.repository;


import com.namecard.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydslRepository {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNum(String phoneNum);

    List<Member> findByEmailOrPhoneNum(String email, String phoneNum);

    Optional<Member> findByMemberId(long memberNo);

}
