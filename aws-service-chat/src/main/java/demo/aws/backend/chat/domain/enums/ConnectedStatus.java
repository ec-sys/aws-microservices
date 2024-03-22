package demo.aws.backend.chat.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum ConnectedStatus {
    ONLINE(0),
    OFFLINE(1),
    INVISIBLE(2);

    @Getter
    final int value;

    ConnectedStatus(int value) {
        this.value = value;
    }

    public static ConnectedStatus of(int status) {
        return Arrays.stream(ConnectedStatus.values()).filter(s -> s.value == status)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "specified status is unsupported. status=" + status)
                );
    }
}
