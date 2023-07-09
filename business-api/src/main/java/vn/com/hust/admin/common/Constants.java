package vn.com.hust.admin.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String SUCCESSFUL = "API000";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String ERROR_SYSTEM = "APP001";
    public static final String ERROR_DUPLICATE = "APP002";
    public static final String ERROR_NOT_NULL = "APP003";
    public static final String ERROR_USER_NAME_EMAIL_EXIST = "APP004";
    public static final String ERROR_UNIQUE = "API500";
    public static final String ERROR_LOGIC = "API501";
    public static final String ERROR_DATA_USING = "API502";
    public static final String ERROR_DATA_NOT_FOUND = "API503";
    public static final String ERROR_MAX_LENGTH = "APP300";
    public static final String ERROR_PASSWORD = "APP301";
    public static final String ERROR_PASSWORD_NOT_NULL = "APP302";
    public static final String ERROR_APP_NOT_COMPARE = "APP303";
    public static final String PERMIT = "APP304";

    public static final String ENCRYPT_ALGORITHM = "SHA";
    public static final String ENCRYPT_ALGORITHM_2 = "MD5";

    public static final String PATTERN_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&])(?=\\S+$).{1,}";

    public static final Long ROOT_ID = -1L;

    public static List<String> APP_CODE_LIST = new ArrayList<>(
            Arrays.asList("ADMIN", "CRM", "USER_NO_ACCOUNT"));

    public interface AppCode {
        String ROOT_NODE = "ROOT_";
        String APP_ADMIN = "ADMIN";
        String APP_CRM = "CRM";
        String APP_USER_NO_ACCOUNT = "USER_NO_ACCOUNT";
    }

    public interface ApDomainUserType {
        String STAFF = "USER_TYPE_STAFF";
        String CUSTOMER = "USER_TYPE_CUSTOMER";
        String CONSTRATOR = "USER_TYPE_CONSTRUCTOR";
        String USER_TYPE = "USER_TYPE";
    }

    public interface CRUD {
        String INSERT = "I";
        String UPDATE = "U";
        String DELETE = "D";
    }
}
