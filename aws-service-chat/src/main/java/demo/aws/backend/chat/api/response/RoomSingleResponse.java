package demo.aws.backend.chat.api.response;

import demo.aws.backend.chat.domain.enums.ConnectedStatus;
import lombok.Data;

import java.util.Date;

@Data
public class RoomSingleResponse {
    private String roomId;
    private String roomName;
    private String avatar;
    private ConnectedStatus status;
    private Date lastOnline;
    private Date lastAction;
}
