package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;
import vn.com.hust.identityservice.sqlite.model.SecGroupPositionCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("SQLiteSecGroupPositionRepository")
public class SecGroupPositionRepository implements SecRepository<SecGroupPositionCache> {
    @Override
    public List<SecGroupPositionCache> findAll() {
        List<SecGroupPositionCache> secGroupPositions = new ArrayList<SecGroupPositionCache>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            String sql = "select group_id, position_id "
                    + " from SEC_GROUP_POSITION ";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SecGroupPositionCache e = SecGroupPositionCache.builder()
                        .groupId(rs.getLong(1))
                        .positionId(rs.getLong(2))
                        .build();
                secGroupPositions.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(pstm);
        }
        return secGroupPositions;
    }

    @Override
    public void saveAll(List<SecGroupPositionCache> list, Connection conn) {
        PreparedStatement pstm = null;
        try {
            String sql = "insert into SEC_GROUP_POSITION (group_id, position_id) " +
                    " values (?,?) ";
            pstm = conn.prepareStatement(sql);
            for (SecGroupPositionCache groupPosition : list) {
                pstm.setLong(1, groupPosition.getGroupId());
                pstm.setLong(2, groupPosition.getPositionId());
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
            String sql = "delete from SEC_GROUP_POSITION";
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqliteConnectionPool.close(pstm);
        }
    }
}
