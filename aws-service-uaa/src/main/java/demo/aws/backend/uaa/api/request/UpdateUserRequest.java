package demo.aws.backend.uaa.api.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRequest {
    private long userId;
    private String email;
    private String phoneNumber;
    private List<Long> newRoles;
    private int testValue;
}
