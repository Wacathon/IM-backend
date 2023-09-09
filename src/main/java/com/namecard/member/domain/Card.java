package com.namecard.member.domain;

import com.namecard.config.AuditBaseEntity;
import com.namecard.member.dto.request.MyProfileRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Card extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "card_seq")
    @Schema(description = "명함 ID")
    private Long cardId;

    @Schema(description = "명함 공개용 이메일")
    private String cardEmail;

    @Schema(description = "사용자 전화번호")
    private String phoneNumber;

    @Schema(description = "자기소개")
    private String introduce;

    @Schema(description = "이메일 공개 여부")
    private boolean isPublicEmail;

    @Schema(description = "전화번호 공개 여부")
    private boolean isPublicPhone;

    @Schema(description = "자기소개 공개 여부")
    private boolean isPublicIntroduce;

    @Builder
    public Card(String cardEmail, String phoneNumber, String introduce, boolean isPublicEmail, boolean isPublicPhone, boolean isPublicIntroduce) {
        this.cardEmail = cardEmail;
        this.phoneNumber = phoneNumber;
        this.introduce = introduce;
        this.isPublicEmail = isPublicEmail;
        this.isPublicPhone = isPublicPhone;
        this.isPublicIntroduce = isPublicIntroduce;
    }

    public void updateCard(MyProfileRequest profileRequest) {
        this.cardEmail = profileRequest.getEmail();
        this.phoneNumber = profileRequest.getPhoneNum();
        this.introduce = profileRequest.getIntroduce();
        this.isPublicEmail = profileRequest.isPublicEmail();
        this.isPublicPhone = profileRequest.isPublicPhone();
        this.isPublicIntroduce = profileRequest.isPublicIntroduce();
    }
}
