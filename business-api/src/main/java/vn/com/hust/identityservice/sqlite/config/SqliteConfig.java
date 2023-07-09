package vn.com.hust.identityservice.sqlite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class SqliteConfig {
    @Value("classpath:sqlite/sec_user-sqlite.ddl")
    Resource secUserDDL;

    @Value("classpath:sqlite/sec_user_ip-sqlite.ddl")
    Resource secUserIpDDL;

    @Value("classpath:sqlite/sec_group_schedule-sqlite.ddl")
    Resource secGroupScheduleDDL;

    @Value("classpath:sqlite/sec_group_module-sqlite.ddl")
    Resource secGroupModuleDDL;

    @Value("classpath:sqlite/sec_group_user-sqlite.ddl")
    Resource secGroupUserDDL;

    @Value("classpath:sqlite/sec_group-sqlite.ddl")
    Resource secGroupTDDL;

    //B1_EVN
    @Value("classpath:sqlite/sec_group_position-sqlite.ddl")
    Resource secGroupPositionDDL;
    //end B1;

    @Bean
    public void initSqlite() throws Exception {
        createDDL(SqliteConnectionPool.getInstance().getCreateConnection());
        createDDL(SqliteConnectionPool.getInstance().getReadConnection());
    }

    public void createDDL(Connection conn) throws Exception {
        String secUser = reSource2String(secUserDDL);
        String secUserIp = reSource2String(secUserIpDDL);
        String secGroupSchedule = reSource2String(secGroupScheduleDDL);
        String secGroupModule = reSource2String(secGroupModuleDDL);
        String secGroupUser = reSource2String(secGroupUserDDL);
        String secGroup = reSource2String(secGroupTDDL);
        //B1_EVN
        String secGroupPosition = reSource2String(secGroupPositionDDL);
        //B1_EVN

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(secUser);
            stmt.execute(secUserIp);
            stmt.execute(secGroupSchedule);
            stmt.execute(secGroup);
            stmt.execute(secGroupModule);
            stmt.execute(secGroupUser);
            stmt.execute(secGroupPosition);//B1_EVN
        } catch (Exception ex) {
            throw ex;
        } finally {
            SqliteConnectionPool.close(stmt);
        }
    }


    public String reSource2String(Resource resource) throws IOException {
        InputStream inputStream = resource.getInputStream();
        String data = null;
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
