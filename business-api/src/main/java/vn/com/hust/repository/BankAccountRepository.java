package vn.com.hust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hust.dao.BankAccountDao;
import vn.com.hust.entity.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, BankAccountDao {
    List<BankAccount> findBankAccountsByDepartment(String department);
}
