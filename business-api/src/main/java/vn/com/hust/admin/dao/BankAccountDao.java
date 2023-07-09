package vn.com.hust.admin.dao;

import vn.com.core.common.generics.GenericDao;
import vn.com.hust.admin.dto.BankAccountDTO;
import vn.com.hust.admin.entity.BankAccount;

import java.util.List;

public interface BankAccountDao extends GenericDao<BankAccount, Long> {
    List<BankAccount> onSearch(BankAccountDTO request);
}
