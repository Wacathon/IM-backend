package com.namecard.indicator.repository;

import com.namecard.indicator.domain.IndicatorConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


@EnableJpaRepositories
public interface IndicatorConnectRepository extends JpaRepository<IndicatorConnect, Long> {
    void deleteByMemberId(long memberId);

    List<IndicatorConnect> findIndicatorConnectsByMemberId(Long memberId);
}
