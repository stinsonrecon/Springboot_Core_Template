package vn.com.hust.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.hust.admin.model.SecUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository("SecUserRepository")
public interface SecUserRepository extends JpaRepository<SecUser, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM SEC_USER " +
                    "WHERE lower(USER_NAME) = lower(?1) " +
                    " LIMIT 1 "
    )
    Optional<SecUser> findFirstByUserNameIgnoreCase(String userName);

    @Query(value = "SELECT * FROM SEC_USER WHERE UPPER(user_name) = UPPER(:userName)", nativeQuery = true)
    List<SecUser> findByUserName(@Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE SEC_USER s " +
                    "SET s.DEPT_ID = ?3, " +
                    "    s.PHONE   = ?2 " +
                    "WHERE s.USER_ID = ?1",
            nativeQuery = true
    )
    void upgradeSecUserToItel(Long userId, String phone, Long deptId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE SEC_USER s " +
                    "SET s.PHONE   = ?2 " +
                    "WHERE s.USER_ID = ?1",
            nativeQuery = true
    )
    void updatePhoneSecUser(Long userId, String phone);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE SEC_USER s " +
                    "SET s.DEPT_ID = ?2, " +
                    "    s.PHONE   = null " +
                    "WHERE s.USER_ID = ?1",
            nativeQuery = true
    )
    void downgradeSecUserToNonItel(Long userId, Long deptId);

    SecUser findByUserId(long userId);
}
