package vn.com.hust.admin.utilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String SUCCESSFUL = "API000";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final int SUCCESS_STATUS_CREATED_USER_KEYCLOAK = 201;

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
    public static final String ERROR_APP305 = "APP305";
    public static final String ERROR_APP306 = "APP306";
    public static final String ERROR_PASS_001 = "APP003";
    public static final String ERROR_PASS_002 = "APP004";
    public static final String ERROR_PASS_003 = "APP005";
    public static final String ERROR_PASS_004 = "APP006";
    public static final String ERROR_PASS_007 = "APP007";
    public static final String ERROR_PASS_008 = "APP008";
    public static final String ERROR_OTP_000 = "OTP000";
    public static final String ERROR_OTP_001 = "OTP001";
    public static final String ERROR_LOCK_TIME = "APP999";
    public static final String ERROR_CREATE_STAFF = "API504";


    // Tai khoan app da duoc lien ket o noi khac
    public static final String ERROR_REQUEST_LK_HAVE = "LKHAVE";

    public static final String ENCRYPT_ALGORITHM = "SHA";

    public static final String ENCRYPT_ALGORITHM_2 = "MD5";

    public static final String RIGHT_CODE_MODULE = "RIGHT_CODE_MODULE";
    public static final String DAY_OF_WEEK = "DAY_OF_WEEK";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String API_SYNC_ORG = "3";
    public static final String API_SYNC_POSITION = "4";
    public static final String API_SYNC_USER = "2";
    public static final String API_SYNC_DEPARTMENT = "5";
    public static final String API_SYNC_GROUP = "6";
    public static final String TYPE_AP_DOMAIN_USER_RECEIVER_NOTIFY = "USER_RECEIVER_NOTIFY";
    public static final String NOTIFY_TYPE_CHANGE_USER = "1";
    public static final String NOTIFY_TITLE_CHANGE_DEPARTMENT_OF_USER = "Thay đổi chức danh";
    public static final String PATTERN_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&])(?=\\S+$).{1,}";


    //DXG DECLARE VARIABLE
    public static final Long IS_MANAGED = 1L;
    public static final Long IS_NOT_MANAGED = 0L;
    public static final Integer USER_NO_ACCOUNT = 0;
    public static final Integer USER_HAS_ACCOUNT = 1;
    public static final Long ROOT_ID = -1L;

    public interface AppCode {
        String ROOT_NODE = "ROOT_";
        String APP_ADMIN = "ADMIN";
        String APP_USER_NO_ACCOUNT = "USER_NO_ACCOUNT";
    }

    public interface ApDomainUserType {
        String STAFF = "USER_TYPE_STAFF";
        String CUSTOMER = "USER_TYPE_CUSTOMER";
        String CONSTRATOR = "USER_TYPE_CONSTRUCTOR";
        String USER_TYPE = "USER_TYPE";
    }


    public interface Realm {
        String MASTER = "master";
    }

    public interface STATUS {
        Long ACTIVE_GROUP = 1L;
    }

    public interface CRUD {
        String INSERT = "I";
        String UPDATE = "U";
        String DELETE = "D";
    }

    public interface TABLE_NAME {
        String TABLE_USER = "USER_ENTITY";


        String SEC_APP = "SEC_APP";
        String SEC_MODULE = "SEC_MODULE";
        String USER_ATTRIBUTE = "USER_ATTRIBUTE";
        String AP_DOMAIN = "AP_DOMAIN";
    }

    public interface USER_ATTRIBUTE_KEY {
        String PHONE_NUMBER = "PHONE";
        String USER_ID = "USER_ID";
        String POSITION_ID = "POSITION_ID";
        String DEPT_ID = "DEPT_ID";
        String ORG_CODE = "ORG_CODE";
        String REFER_CODE = "REFER_CODE";
        String LIST_JOB_TITLE_CODE = "LIST_JOB_TITLE_CODE";
        String DOB = "BIRTHDAY";
        String SEX = "SEX";
        String CREATED_AT = "CREATED_AT";
        String CREATED_BY = "CREATED_BY";
        String UPDATED_AT = "UPDATED_AT";
        String UPDATED_BY = "UPDATED_BY";
        String TENANT_ID = "TENANT_ID";
        String THEME_ID = "THEME_ID";
    }

    public interface UPLOAD_PARAM {
        String UPLOAD_FILE_PARAM = "UPLOAD_FILE_PARAM";
        String IGNORE_LIST_FILE_EXTENTION = "IGNORE_LIST_FILE_EXTENTION";
        String MAX_COUNT_FILE_UPLOAD = "MAX_COUNT_FILE_UPLOAD";
        String MAX_FILE_SIZE = "MAX_FILE_SIZE";
    }

    public interface REPONSE_API {
        String CON_CODE_SUCCESS = "200";
        String CON_STATUS_CODE_SUCCESS = "OK";

        String IHRP_CODE_SUCCESS = "1";
        String IHRP_STATUS_CODE_SUCCESS = "SUCCESS";

        String CFS_CODE_SUCCESS = "200";
        String CFS_CODE_SUCCESS_1 = "API-001";
        String CFS_CODE_SUCCESS_2 = "API-002";
        String CFS_STATUS_CODE_SUCCESS = "SUCCESS";
    }

    public interface PERMISSION_TYPE {
        Long RIGHT_PERMISSION = 0L;
        Long EXCEPTION_PERMISSION = 1L;
        Long CONCURRENTLY_PERMISSION = 2L;
    }

}
