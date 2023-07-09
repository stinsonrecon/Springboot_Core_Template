package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecGroupUserCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecGroupUserRepository")
public class SecGroupUserRepository implements SecRepository<SecGroupUserCache> {

    @Override
    public List<SecGroupUserCache> findAll() {
        List<SecGroupUserCache> secGroupUsers = new ArrayList<SecGroupUserCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id, user_id "
                    + " from SEC_GROUP_USER ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupUserCache e = SecGroupUserCache.builder()
                        .groupId(rs.getLong(1))
                        .userId(rs.getLong(2))
                        .build();
                secGroupUsers.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroupUsers;
    }

    @Override
    public void saveAll(List<SecGroupUserCache> secGroupUsers, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_GROUP_USER (group_id, user_id) " +
                    " values (?,?) ";
            pstm = conn.prepareStatement(sql);
            for (SecGroupUserCache groupUser : secGroupUsers) {
                pstm.setLong(1, groupUser.getGroupId());
                pstm.setLong(2, groupUser.getUserId());
                pstm.addBatch();
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
            String sql = "delete from SEC_GROUP_USER";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }

    }

    public List<Long> findGroupsByUserId(Long userId) {
        List<Long> secGroups = new ArrayList<Long>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id "
                    + " from SEC_GROUP_USER where user_id = ? ";

            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, userId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                secGroups.add(rs.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroups;
    }

}
