package com.namecard.indicator.domain;

import com.namecard.config.AuditBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IndicatorConnect extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "indicator_connect_seq")
    @Schema(description = "Indicator Connect Id(PK)")
    private long indicatorConnectId;

    private long tagId;

    private long memberId;

}
