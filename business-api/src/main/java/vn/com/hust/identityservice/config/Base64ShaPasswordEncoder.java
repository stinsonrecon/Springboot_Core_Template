package vn.com.hust.identityservice.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import vn.com.core.common.exceptions.AppException;
import vn.com.core.common.utils.BASE64Encoder;
import vn.com.hust.admin.common.Constants;

import java.security.MessageDigest;

public class Base64ShaPasswordEncoder implements PasswordEncoder {

    private static String encrypt(String strValue) throws Exception {
        return encrypt(strValue.getBytes(), Constants.ENCRYPT_ALGORITHM);
    }

    public static String encrypt(String strValue, String strAlgorithm) {
        try {
            return encrypt(strValue.getBytes(), strAlgorithm);
        } catch (Exception e) {
            throw new AppException("ERROR.ENCRYPT");
        }
    }

    public static String encrypt(byte[] btValue, String strAlgorithm) throws Exception {
        BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        return enc.encodeBuffer(md.digest(btValue));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String result = "";
        try {
            result = encrypt(rawPassword.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean result = false;
        try {
            result = encrypt(rawPassword.toString()).equals(encodedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
