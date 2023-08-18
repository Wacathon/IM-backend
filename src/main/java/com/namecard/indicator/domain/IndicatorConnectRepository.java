package com.namecard.indicator.domain;

import com.namecard.indicator.dto.entity.IndicatorConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
public interface IndicatorConnectRepository extends JpaRepository<IndicatorConnect, Long> {
    void deleteByUserId(long userId);
}
