package demo.aws.backend.rtm.config;

public class DestinationConfig {
    public static class ChatRoom {
        public static String publicMessages(String roomId) {
            return "/topic/" + roomId + ".public.messages";
        }

        public static String privateMessages(String roomId) {
            return "/queue/" + roomId + ".private.messages";
        }

        public static String connectedUsers(String roomId) {
            return "/topic/" + roomId + ".connected.users";
        }
    }
}
