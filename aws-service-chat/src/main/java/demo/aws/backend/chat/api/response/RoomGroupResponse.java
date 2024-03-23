package demo.aws.backend.chat.api.response;

import lombok.Data;

@Data
public class RoomGroupResponse {
    private String roomId;
    private String roomName;
    private String avatar;
}
