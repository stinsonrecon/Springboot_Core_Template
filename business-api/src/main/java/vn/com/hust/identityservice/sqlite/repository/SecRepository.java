package vn.com.hust.identityservice.sqlite.repository;

import java.sql.Connection;
import java.util.List;

public interface SecRepository<T> {
    public List<T> findAll();

    public void saveAll(List<T> list, Connection conn);

    public void delete(Connection conn);
}
