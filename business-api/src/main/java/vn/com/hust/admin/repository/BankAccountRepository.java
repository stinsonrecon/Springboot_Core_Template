package vn.com.hust.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hust.admin.dao.BankAccountDao;
import vn.com.hust.admin.entity.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, BankAccountDao {
    List<BankAccount> findBankAccountsByDepartment(String department);
}
