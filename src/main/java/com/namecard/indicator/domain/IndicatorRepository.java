package com.namecard.indicator.domain;

import com.namecard.indicator.dto.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
public interface IndicatorRepository extends JpaRepository<Indicator, Long>, IndicatorQuerydslRepository {
}
