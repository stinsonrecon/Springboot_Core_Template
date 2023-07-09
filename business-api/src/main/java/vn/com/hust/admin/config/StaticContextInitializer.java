package vn.com.hust.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import vn.com.hust.admin.utilities.PropertiesUtil;

import javax.annotation.PostConstruct;

@Component
public class StaticContextInitializer {

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        PropertiesUtil.setEnviroment(env);
    }
}
