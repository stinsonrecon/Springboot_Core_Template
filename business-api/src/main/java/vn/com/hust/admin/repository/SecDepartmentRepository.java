package vn.com.hust.admin.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.hust.admin.model.SecDepartment;

/**
 * @author TienLM20
 * @since 09/07/2023
 */

@Repository
public interface SecDepartmentRepository extends JpaRepository<SecDepartment, Long> {
    @Query(value = "select d.*\n" +
            "from v_sec_user e,\n" +
            "     sec_department d\n" +
            "where e.DEPT_ID = d.DEPT_ID\n" +
            "  and d.status = '1'\n" +
            "  and e.USER_NAME = (select user_name\n" +
            "                     from sec_user ad\n" +
            "                     where (ad.user_name = upper(:userName))\n" +
            "                       and ad.status = 1)\n" +
            "  and e.STATUS = 1", nativeQuery = true)
    SecDepartment getDepartmentInfoByUserName(@Param("userName") String userName);
}
