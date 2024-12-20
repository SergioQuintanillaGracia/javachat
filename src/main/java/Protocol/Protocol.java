package Protocol;

import java.util.Base64;

public class Protocol {
    public static final String PROTOCOL_PREF = "$";
    public static final String MESSAGE_PREFIX = "M";

    public static class Server {
        public static final String AUTH_REQUEST = PROTOCOL_PREF + "auth_request";
        public static final String EMPTY_USER_OR_PASSWORD = PROTOCOL_PREF + "empty_user_or_password";
        public static final String USERNAME_OUT_OF_RANGE = PROTOCOL_PREF + "username_out_of_range";
        public static final String AUTH_USER_NOT_REGISTERED = PROTOCOL_PREF + "auth_user_not_registered";
        public static final String AUTH_WRONG_PASSWORD = PROTOCOL_PREF + "auth_wrong_password";
        public static final String AUTH_USER_ALREADY_LOGGED = PROTOCOL_PREF + "auth_already_logged";
        public static final String AUTH_SUCCESSFUL = PROTOCOL_PREF + "auth_successful";
        public static final String USER_CREATION_USER_ALREADY_EXISTS = PROTOCOL_PREF +
                "user_creation_user_already_exists";
        public static final String USER_CREATION_SUCCESSFUL = PROTOCOL_PREF + "user_creation_successful";
        public static final String CAN_TYPE = PROTOCOL_PREF + "can_type";
        public static final String MESSAGE_TOO_LONG = PROTOCOL_PREF + "message_too_long";
        public static final String MESSAGE_HISTORY_END = PROTOCOL_PREF + "message_history_end";
    }

    public static class Client {
        public static final String AUTH_CREATE_USER = PROTOCOL_PREF + "auth_create_new_user";
        public static final String LOAD_MESSAGE_HISTORY = PROTOCOL_PREF + "load_message_history";
    }

    public static class AuthData {
        public static int MIN_USERNAME_LENGTH = 3;
        public static int MAX_USERNAME_LENGTH = 14;

        private static final String separator = ":";
        private final String username;
        private final String password;

        public AuthData(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        /*
        * Returns a string version of AuthData in the form `username:password`, encoded
        * in base64
        */
        @Override
        public String toString() {
            String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes());
            String encodedPassword;

            if (password != null) {
                encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            } else {
                encodedPassword = "";
            }

            return "%s:%s".formatted(encodedUsername, encodedPassword);
        }

        public static AuthData fromString(String str) {
            String dataParts[] = str.split(":");

            if (dataParts.length != 2) {
                System.err.printf("Cannot form an AuthData object from the string %s\n", str);
                return null;
            }

            String decodedUsername = new String(Base64.getDecoder().decode(dataParts[0]));
            String decodedPassword = new String(Base64.getDecoder().decode(dataParts[1]));

            return new AuthData(decodedUsername, decodedPassword);
        }
    }
}
