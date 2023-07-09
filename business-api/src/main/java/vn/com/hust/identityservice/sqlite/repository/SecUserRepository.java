package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecUserCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecUserRepository")
public class SecUserRepository implements SecRepository<SecUserCache> {

    public List<SecUserCache> findAll() {
        List<SecUserCache> secUsers = new ArrayList<SecUserCache>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select user_id, user_name, password_expire_status, "
                    + "status, login_failure_count, position_id from SEC_USER ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecUserCache e = SecUserCache.builder().userId(rs.getLong(1)).userName(rs.getString(2))
                        .passwordExpireStatus(rs.getString(3)).status(rs.getInt(4)).loginFailureCount(rs.getInt(5))
                        .build();
                secUsers.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secUsers;
    }

    public void saveAll(List<SecUserCache> secUsers, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_USER (user_id, user_name, password_expire_status, "
                    + "status, login_failure_count, position_id ) values (?,?,?,?,?,?) ";
            pstm = conn.prepareStatement(sql);
            for (SecUserCache user : secUsers) {
                pstm.setLong(1, user.getUserId());
                pstm.setString(2, user.getUserName());
                pstm.setString(3, user.getPasswordExpireStatus());
                pstm.setInt(4, user.getStatus());
                pstm.setInt(5, user.getLoginFailureCount());
                pstm.setLong(6, user.getPositionId() == null ? 0L : user.getPositionId());
                pstm.addBatch();
            }
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
            String sql = "delete from SEC_USER";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }

    public List<SecUserCache> findByUserName(String username) {
        List<SecUserCache> secUsers = new ArrayList<SecUserCache>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select user_id, user_name, password_expire_status, "
                    + "status, login_failure_count , position_id from SEC_USER where user_name = ?";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecUserCache e = SecUserCache.builder().userId(rs.getLong(1)).userName(rs.getString(2))
                        .passwordExpireStatus(rs.getString(3)).status(rs.getInt(4)).loginFailureCount(rs.getInt(5))
                        .build();
                secUsers.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secUsers;
    }
}


