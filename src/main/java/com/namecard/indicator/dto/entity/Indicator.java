package com.namecard.indicator.dto.entity;

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
public class Indicator extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "indicator_seq")
    @Schema(description = "Indicator Id(PK)")
    private long indicatorId;

    @Schema(description = "태그 ID(FK)")
    private long tagId;

    @Schema(description = "태그 점수")
    private int tagScore;

    @Schema(description = "피드백 ID(FK)")
    private long feedbackId;

    @Schema(description = "유저 ID(FK)")
    private long userId;
}
