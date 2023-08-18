package demo.aws.backend.uaa.api.response;

import demo.aws.backend.uaa.domain.model.TokenInfo;
import lombok.Data;

@Data
public class LoginResponse {
    private TokenInfo token;
    private long userId;
    private String avatar;
    private String firstName;
    private String lastName;
    private String note;
}
