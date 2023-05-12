package vn.com.hust;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import vn.com.core.common.config.CommonConfig;

@EnableAsync
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan({"vn.com.*"})
@EnableJpaRepositories("vn.com.*")
@EntityScan("vn.com.*")
@Import(CommonConfig.class)
@EnableRedisRepositories
public class BusinessApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
