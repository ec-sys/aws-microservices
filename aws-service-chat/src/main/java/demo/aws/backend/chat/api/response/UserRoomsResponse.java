package demo.aws.backend.chat.api.response;

import lombok.Data;

import java.util.List;

@Data
public class UserRoomsResponse {
    List<RoomGroupResponse> groupRooms;
    List<RoomSingleResponse> singleRooms;
}
