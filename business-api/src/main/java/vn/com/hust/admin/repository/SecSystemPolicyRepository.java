package vn.com.hust.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.hust.admin.model.SecSystemPolicy;

import java.util.Collection;
import java.util.List;

@Repository
public interface SecSystemPolicyRepository extends JpaRepository<SecSystemPolicy, String> {

    @Query("SELECT s FROM SecSystemPolicy s")
    List<SecSystemPolicy> findAll();

    @Query("SELECT s FROM SecSystemPolicy s WHERE s.policyType NOT IN :types")
    List<SecSystemPolicy> findNotInType(@Param("types") Collection<String> types);

    @Query("SELECT s FROM SecSystemPolicy s WHERE UPPER (s.policyType) = UPPER (:policyType)")
    List<SecSystemPolicy> findByType(@Param("policyType") String policyType);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SEC_SYSTEM_POLICY SET policy_value = NULL", nativeQuery = true)
    int resetValue();

    @Modifying
    @Transactional
    @Query(value = "UPDATE SecSystemPolicy s SET s.policyValue = :policyValue WHERE UPPER (s.policyType) = UPPER (:policyType)")
    int updateValue(@Param("policyType") String policyType, @Param("policyValue") String policyValue);

    SecSystemPolicy findByPolicyType(String policyType);

}
