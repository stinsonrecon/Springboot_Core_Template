package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecUserIpCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecUserIpRepository")
public class SecUserIpRepository implements SecRepository<SecUserIpCache> {

    public List<SecUserIpCache> findAll() {
        List<SecUserIpCache> secUserIps = new ArrayList<SecUserIpCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select  user_id, user_name, "
                    + "ipStart, ipEnd from SEC_USER_IP ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecUserIpCache e = SecUserIpCache.builder()
                        .userId(rs.getLong(1))
                        .userName(rs.getString(2))
                        .ipStart(rs.getLong(3))
                        .ipEnd(rs.getLong(4))
                        .build();
                secUserIps.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secUserIps;
    }

    public void saveAll(List<SecUserIpCache> secUserIps, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_USER_IP (user_id, user_name, "
                    + "ipStart, ipEnd) values (?,?,?,?) ";
            pstm = conn.prepareStatement(sql);
            for (SecUserIpCache userIp : secUserIps) {
                pstm.setLong(1, userIp.getUserId());
                pstm.setString(2, userIp.getUserName());
                pstm.setLong(3, userIp.getIpStart());
                pstm.setLong(4, userIp.getIpEnd());
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
            String sql = "delete from SEC_USER_IP";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }

    public List<SecUserIpCache> findAllByUserName(String username) {
        List<SecUserIpCache> secUserIps = new ArrayList<SecUserIpCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select user_id, user_name, "
                    + "ipStart, ipEnd from SEC_USER_IP where user_name = ?";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecUserIpCache e = SecUserIpCache.builder()
                        .userId(rs.getLong(1))
                        .userName(rs.getString(2))
                        .ipStart(rs.getLong(3))
                        .ipEnd(rs.getLong(4))
                        .build();
                secUserIps.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secUserIps;
    }
}
