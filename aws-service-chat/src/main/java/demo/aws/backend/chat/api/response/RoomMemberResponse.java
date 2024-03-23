package demo.aws.backend.chat.api.response;

import lombok.Data;

@Data
public class RoomMemberResponse {
    private String userId;
    private String memberId;
}
