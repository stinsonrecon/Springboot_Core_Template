package vn.com.hust.service;

import vn.com.core.common.generics.GenericService;
import vn.com.hust.dto.BankAccountDTO;
import vn.com.hust.entity.BankAccount;

public interface BankAccountService extends GenericService<BankAccount, Long> {
    Object findAllByDepartment(BankAccount request);

    Object onSearchBankAccount(BankAccountDTO request);
}
