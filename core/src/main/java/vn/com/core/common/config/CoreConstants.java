package vn.com.core.common.config;

public interface CoreConstants {
    String API_LOGIN = "/login";

    String ENCODE = "utf-8";
    String APP_NAME = "Software solutions java library";
    String APP_VERSION = "2.0.0";
    String APP_KEY = "190782.ydsiufhdjkshu658436uidshfe86r587436.081081";

    interface PRIVILEGE {
        String UPDATE = "UPDATE";
        String INSERT = "INSERT";
        String DELETE = "DELETE";
        String VIEW = "VIEW";
    }

    interface MESSAGE {
        String SUCCESS = "Success";
        String CREAT_SUCCESS = "Create success";
        String UPDATE_SUCCESS = "Update success";
        String DELETE_SUCCESS = "Update success";
        String USER_NOT_FOUND = "User not found";
        String AUTHEN_FAIL = "Authen fail";
        String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    }

    interface SYSTEM_ERROR_CODE {

        String ORACLE_UNIQUE_CONSTRAINT_VIOLATED = "ORA-00001";
        String ORACLE_MAX_LENGTH = "ORA-12899";
        String ORACLE_INVALID_ROWID = "ORA-01410";
    }

    interface REQUEST_ATTR {
        String REQUEST_ID = "request_id";
        String USER_NAME = "user_name";
        String USER_ID = "user_id";
        String SESSION_ID = "session_id";
    }

    interface HEADER_KEY {
        String REQUEST_PAIR = "request_pair";
        String DEVICE_ID = "device_id";
        String APP_CODE = "app_code";
    }

    interface CLAIM_KEY {
        String MAIN_ID = "mainId";
        String USER_ID = "userId";
        String USER_ID_2 = "user_id";
        String SUB = "sub";// user id from key cloak
        String USER_NAME = "userName";
        String USER_NAME_2 = "preferred_username";
        String FULL_NAME = "fullName";
        String PHONE = "fullName";

        String ORG_ID = "orgId";
        String GROUPS = "groups";
        String DEPT_ID = "deptId";

        String SESSION_ID = "sessionId";
    }
}
