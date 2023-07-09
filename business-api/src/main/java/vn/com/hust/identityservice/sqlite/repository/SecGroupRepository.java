package vn.com.hust.identityservice.sqlite.repository;


import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecGroupCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository("SQLiteSecGroupRepository")
public class SecGroupRepository implements SecRepository<SecGroupCache> {

    @Override
    public List<SecGroupCache> findAll() {
        List<SecGroupCache> secGroups = new ArrayList<SecGroupCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id, child_level "
                    + " from SEC_GROUP ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupCache e = SecGroupCache.builder()
                        .groupId(rs.getLong(1))
                        .childLevel(rs.getInt(2))
                        .build();
                secGroups.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroups;
    }

    @Override
    public void saveAll(List<SecGroupCache> secGroupCaches, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_GROUP (group_id, child_level) values (?,?) ";
            pstm = conn.prepareStatement(sql);
            int i = 0;
            for (SecGroupCache secGroupCache : secGroupCaches) {
                pstm.setLong(1, secGroupCache.getGroupId());
                pstm.setInt(2, secGroupCache.getChildLevel());
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
            String sql = "delete from SEC_GROUP";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }

    }
}
