package com.namecard.feedback.domian;

import com.namecard.exception.NotFoundException;

public enum Relationship {
    SCHOOL_COLLEAGUE,
    COMPANY_COLLEAGUE,
    FRIEND,
    ETC;

    public static Relationship findRelationshipByString(String relationship) {
        for (Relationship r : Relationship.values()) {
            if (r.name().equalsIgnoreCase(relationship))
                return r;
        }
        throw new NotFoundException("존재하지 않는 관계입니다.");
    }
}
