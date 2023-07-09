package vn.com.hust.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.hust.admin.model.SecAccessLog;

@Repository
public interface SecAccessLogRepository extends JpaRepository<SecAccessLog, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SEC_ACCESS_LOG  " +
            " WHERE user_id=:userId " +
            " AND module_id=:moduleId " +
            " AND access_date >= TO_DATE (:fromDate, 'dd/MM/yyyy') " +
            " AND access_date < TO_DATE (:toDate, 'dd/MM/yyyy') + 1 ",
            nativeQuery = true)
    int deleteAccessLog(@Param("userId") Long userId, @Param("moduleId") Long moduleId, @Param("toDate") String fromDate, @Param("fromDate") String toDate);

}
