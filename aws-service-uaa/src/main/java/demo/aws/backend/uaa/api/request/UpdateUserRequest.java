package demo.aws.backend.uaa.api.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private long userId;
    private String email;
    private String phoneNumber;
}
