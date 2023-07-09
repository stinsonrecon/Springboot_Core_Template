package vn.com.hust.admin.service;

import vn.com.core.common.generics.GenericService;
import vn.com.hust.admin.entity.BankAccount;
import vn.com.hust.admin.dto.BankAccountDTO;

public interface BankAccountService extends GenericService<BankAccount, Long> {
    Object findAllByDepartment(BankAccount request);

    Object onSearchBankAccount(BankAccountDTO request);
}
