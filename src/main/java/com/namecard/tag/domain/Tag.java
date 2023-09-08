package com.namecard.tag.domain;

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
public class Tag extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tag_seq")
    @Schema(description = "Tag Id(PK)")
    private long tagId;

    @Schema(description = "태그 명")
    private String tagName;
}
