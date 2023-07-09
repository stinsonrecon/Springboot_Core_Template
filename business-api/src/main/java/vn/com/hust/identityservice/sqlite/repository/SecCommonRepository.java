package vn.com.hust.identityservice.sqlite.repository;

import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.sqlite.database.SqliteConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository("SQLiteSecCommonRepository")
public class SecCommonRepository {

    public boolean isAccessToPathByGroup(String path, List<Long> groups) throws Exception {
        boolean isAccess = true;
        PreparedStatement prm = null;
        ResultSet rs = null;
        try {
            Connection conn = SqliteConnectionPool.getInstance().getReadConnection();
            if (groups.isEmpty()) {
                isAccess = false;
            } else {
                String sql = " select count(1) FROM " +
                        " (SELECT sgm.path, sgm.access_type, sg.group_id, sg.child_level " +
                        " FROM " +
                        "    SEC_GROUP_MODULE   sgm,  " +
                        "    (select group_id, child_level from SEC_GROUP where group_id in ( ";
                sql = genInClause(sql, groups);
                sql += " ) sg " +
                        " WHERE " +
                        "	sgm.group_id = sg.group_id " +
                        "	and path = ?) smr " +
                        " GROUP BY smr.path	" +
                        " HAVING  (MAX (2 * smr.child_level + smr.access_type) % 2) > 0 ";
                prm = conn.prepareStatement(sql);
                prm.setString(1, path);
                rs = prm.executeQuery();
                if (!rs.next() || rs.getInt(1) == 0) {
                    isAccess = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            SqliteConnectionPool.close(rs);
            SqliteConnectionPool.close(prm);
        }
        return isAccess;
    }

    public String genInClause(String sql, List<Long> groups) {
        sql += groups.get(0);
        for (int i = 1; i < groups.size(); i++) {
            sql += ", " + groups.get(i);
        }
        sql += ") ";
        return sql;
    }
}
