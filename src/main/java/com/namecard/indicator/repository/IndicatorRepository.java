package com.namecard.indicator.repository;

import com.namecard.indicator.domain.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
public interface IndicatorRepository extends JpaRepository<Indicator, Long>, IndicatorQuerydslRepository {
}
