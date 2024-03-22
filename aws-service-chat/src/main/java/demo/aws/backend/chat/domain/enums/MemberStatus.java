package demo.aws.backend.chat.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum MemberStatus {

    JOINED(0),
    LEFT(1),
    DELETED(2),
    APPROVAL_PENDING(3);

    @Getter
    final int value;

    MemberStatus(int value) {
        this.value = value;
    }

    public static MemberStatus of(int status) {
        return Arrays.stream(MemberStatus.values()).filter(s -> s.value == status)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "specified status is unsupported. status=" + status)
                );
    }
}