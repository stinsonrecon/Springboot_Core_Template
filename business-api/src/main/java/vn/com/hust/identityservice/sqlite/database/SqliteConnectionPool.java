package vn.com.hust.identityservice.sqlite.database;

import java.sql.*;

public final class SqliteConnectionPool {

    private static Connection CONNECTION_FIRST;
    private static Connection CONNECTION_SECOND;
    private static boolean flag = true;

    private SqliteConnectionPool() {
        try {
            //
            Class.forName("org.sqlite.JDBC");
            CONNECTION_FIRST = DriverManager.getConnection("jdbc:sqlite::memory:");
            CONNECTION_FIRST.setAutoCommit(false);

            //
            CONNECTION_SECOND = DriverManager.getConnection("jdbc:sqlite::memory:");
            CONNECTION_SECOND.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqliteConnectionPool getInstance() {
        return SqliteConnectionPoolHolder.INSTANCE;
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(Statement prm) {
        try {
            if (prm != null)
                prm.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(PreparedStatement prm) {
        try {
            if (prm != null)
                prm.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Connection getCreateConnection() {
        return flag ? CONNECTION_SECOND : CONNECTION_FIRST;
    }

    public Connection getReadConnection() {
        return flag ? CONNECTION_FIRST : CONNECTION_SECOND;
    }

    public void toSwitch() {
        flag = !flag;
    }

    private static class SqliteConnectionPoolHolder {

        private static final SqliteConnectionPool INSTANCE = new SqliteConnectionPool();
    }
}
