package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecGroupScheduleCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecGroupScheduleRepository")
public class SecGroupScheduleRepository implements SecRepository<SecGroupScheduleCache> {

    public List<SecGroupScheduleCache> findAll() {
        List<SecGroupScheduleCache> secGroupSchedules = new ArrayList<SecGroupScheduleCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id, group_name, "
                    + "access_time_start, access_time_end from SEC_GROUP_SCHEDULE ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupScheduleCache e = SecGroupScheduleCache.builder().groupId(rs.getLong(1))
                        .groupName(rs.getString(2)).accessTimeStart(rs.getString(3)).accessTimeEnd(rs.getString(4))
                        .build();
                secGroupSchedules.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroupSchedules;
    }

    public void saveAll(List<SecGroupScheduleCache> secGroupSchedules, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_GROUP_SCHEDULE (group_id, group_name, "
                    + "access_time_start, access_time_end) values (?,?,?,?) ";
            pstm = conn.prepareStatement(sql);
            for (SecGroupScheduleCache groupSchedule : secGroupSchedules) {
                pstm.setLong(1, groupSchedule.getGroupId());
                pstm.setString(2, groupSchedule.getGroupName());
                pstm.setString(3, groupSchedule.getAccessTimeStart());
                pstm.setString(4, groupSchedule.getAccessTimeEnd());
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
            String sql = "delete from SEC_GROUP_SCHEDULE";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }

    public List<SecGroupScheduleCache> findAllByGroupId(Long groupId) {
        List<SecGroupScheduleCache> secGroupSchedules = new ArrayList<SecGroupScheduleCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select  group_id, group_name, "
                    + "access_time_start, access_time_end from SEC_GROUP_SCHEDULE where group_id = ? ";

            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, groupId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupScheduleCache e = SecGroupScheduleCache.builder().groupId(rs.getLong(1))
                        .groupName(rs.getString(2)).accessTimeStart(rs.getString(3)).accessTimeEnd(rs.getString(4))
                        .build();
                secGroupSchedules.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroupSchedules;
    }
}
