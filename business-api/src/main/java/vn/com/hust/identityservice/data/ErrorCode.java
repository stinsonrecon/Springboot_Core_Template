package vn.com.hust.identityservice.data;

public class ErrorCode {

    public static final String ERROR_000 = "Bad credentials";
    //public static final String ERROR_001 = "User credentials have expired";
    public static final String ERROR_001 = "Mật khẩu của người dùng đã hết hạn. Vui lòng liên hệ Quản trị hệ thống";
    //public static final String ERROR_002 = "This IP address is not allow to Login this time";
    public static final String ERROR_002 = "Địa chỉ IP của bạn không hợp lệ";
    //public static final String ERROR_003 = "Account locked";
    public static final String ERROR_003 = "Tài khoản của người dùng đã bị khóa. Vui lòng liên hệ Quản trị hệ thống";
    public static final String ERROR_012 = "Tài khoản của người dùng đã bị khóa trong XXXXXX phút.";
    public static final String ERROR_004 = "User is disabled";
    public static final String ERROR_005 = "User is not set schedule";
    public static final String ERROR_006 = "User is not set IP permission";
    //public static final String ERROR_007 = "User is not allow to Login this schedule";
    public static final String ERROR_007 = "Tài khoản đăng nhập theo lịch truy cập chưa đúng. Vui lòng liên hệ Quản trị hệ thống";
    public static final String ERROR_008 = "User is not set to any group";
    //public static final String ERROR_009 = "User or password is invalid";
    public static final String ERROR_009 = "Tài khoản hoặc mật khẩu không đúng";
    public static final String ERROR_010 = "Authenticate failed in Ldap System";
    public static final String ERROR_011 = "Staff not found";
    public static final String ERROR_013 = "Bạn đã nhập lại OTP quá XXXXXX cho phép.Mời đăng nhập lại để nhận mã OTP!";

    public static final String ERROR_014 = "Bạn đã bị khóa trong XXXXXX phút do nhập sai liên tiếp OTP YYYYYY lần!";

    public static final String ERROR_015 = "Không thể xác thực thông tin tài khoản Social";
    public static final String ERROR_016 = "Không thể đăng nhập tài khoản app";
    public static final String ERROR_017 = "Không thể liên kết tài khoản app";
    public static final String ERROR_018 = "Không thể hủy liên kết tài khoản app";
    public static final String ERROR_019 = "Chưa thể liên kết 2 tài khoản: Có các phương thức đăng nhập trùng nhau.";

    public static final String ERROR_999 = "Unknow error";

}
