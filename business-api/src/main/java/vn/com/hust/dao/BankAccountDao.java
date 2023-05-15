package vn.com.hust.dao;

import vn.com.core.common.generics.GenericDao;
import vn.com.hust.dto.BankAccountDTO;
import vn.com.hust.entity.BankAccount;

import java.util.List;

public interface BankAccountDao extends GenericDao<BankAccount, Long> {
    List<BankAccount> onSearch(BankAccountDTO request);
}
