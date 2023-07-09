package vn.com.hust.identityservice.sqlite.repository;


import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecGroupModuleCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecGroupModuleRepository")
public class SecGroupModuleRepository implements SecRepository<SecGroupModuleCache> {

    @Override
    public List<SecGroupModuleCache> findAll() {
        List<SecGroupModuleCache> secGroupModules = new ArrayList<SecGroupModuleCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id, path "
                    + " from SEC_GROUP_MODULE ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupModuleCache e = SecGroupModuleCache.builder()
                        .groupId(rs.getLong(1))
                        .path(rs.getString(2))
                        .build();
                secGroupModules.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroupModules;
    }

    @Override
    public void saveAll(List<SecGroupModuleCache> secGroupModuleCaches, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_GROUP_MODULE (group_id, path, access_type) values (?,?,?) ";
            pstm = conn.prepareStatement(sql);
            int i = 0;
            for (SecGroupModuleCache secGroupModuleCache : secGroupModuleCaches) {
                pstm.setLong(1, secGroupModuleCache.getGroupId());
                pstm.setString(2, secGroupModuleCache.getPath());
                pstm.setLong(3, secGroupModuleCache.getAccessType());
                pstm.addBatch();
                i++;
                if (i % 2000 == 0) {
                    pstm.executeBatch();
                    conn.commit();
                }
            }
            //
            pstm.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }

    @Override
    public void delete(Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "delete from SEC_GROUP_MODULE";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }

}
