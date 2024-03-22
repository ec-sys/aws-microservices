package demo.aws.backend.chat.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum RoomType {
    INDIVIDUAL (0),
    GROUP(1);

    @Getter
    final int value;

    RoomType(int value) {
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