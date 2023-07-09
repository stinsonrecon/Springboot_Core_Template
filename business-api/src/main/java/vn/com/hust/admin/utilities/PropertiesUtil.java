package vn.com.hust.admin.utilities;

import org.springframework.core.env.Environment;

/**
 * Author: PhucVM
 * Date: 15/08/2019
 * Time: 17:07:40
 */
public final class PropertiesUtil {

    private static Environment env;

    private PropertiesUtil() {
    }

    public static void setEnviroment(Environment env) {
        PropertiesUtil.env = env;
    }

    public static String getProperty(String property) {
        return env == null ? null : env.getProperty(property);
    }
}
